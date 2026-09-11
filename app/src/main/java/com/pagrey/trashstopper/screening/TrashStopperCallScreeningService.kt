package com.pagrey.trashstopper.screening

import android.telecom.Call
import android.telecom.CallScreeningService
import com.pagrey.trashstopper.data.CallEventEntity
import com.pagrey.trashstopper.data.TrashStopperDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/** Android Telecom entry point. Keep the decision path synchronous and local. */
class TrashStopperCallScreeningService : CallScreeningService() {
    private val engine = RuleAwareScreeningEngine()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onScreenCall(callDetails: Call.Details) {
        if (callDetails.callDirection != Call.Details.DIRECTION_INCOMING) {
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
                response.setDisallowCall(true)
                response.setRejectCall(true)
                response.setSkipNotification(true)
                response.setSkipCallLog(false)
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
}
