package com.pagrey.trashstopper.screening

import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import com.pagrey.trashstopper.data.CallEventEntity
import com.pagrey.trashstopper.data.TrashStopperDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/** Android Telecom entry point. Keep the decision path synchronous and local. */
class TrashStopperCallScreeningService : CallScreeningService() {
    private val engine = RuleAwareScreeningEngine()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onScreenCall(callDetails: Call.Details) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            callDetails.callDirection != Call.Details.DIRECTION_INCOMING
        ) {
            respondToCall(callDetails, CallResponse.Builder().build())
            return
        }

        val number = PhoneNumberNormalizer.normalize(callDetails.handle?.schemeSpecificPart)
        val decision = engine.evaluate(number)
        val response = CallResponse.Builder()

        when (decision.action) {
            ScreeningDecision.Action.ALLOW -> Unit
            ScreeningDecision.Action.SILENCE -> response.setSilenceCall(true)
            ScreeningDecision.Action.BLOCK -> {
                // A third-party screening service may disallow and reject the call.
                // Do not attempt to hide the blocked call from the system call log.
                response.setDisallowCall(true)
                response.setRejectCall(true)
                response.setSkipNotification(true)
            }
        }

        // Respond first: persistence must never consume the Telecom screening budget.
        respondToCall(callDetails, response.build())

        if (number.isNotBlank()) {
            scope.launch {
                TrashStopperDataStore(this@TrashStopperCallScreeningService).saveCallEvent(
                    CallEventEntity(
                        phoneNumber = number,
                        result = decision.reason,
                        riskScore = decision.riskScore,
                        action = decision.action.name
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}
