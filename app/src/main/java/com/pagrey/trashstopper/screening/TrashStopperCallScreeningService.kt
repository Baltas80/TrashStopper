package com.pagrey.trashstopper.screening

import android.telecom.Call
import android.telecom.CallScreeningService

/** Android Telecom entry point. Keep this path local and bounded. */
class TrashStopperCallScreeningService : CallScreeningService() {
    private val engine = LocalScreeningEngine()

    override fun onScreenCall(callDetails: Call.Details) {
        if (callDetails.callDirection != Call.Details.DIRECTION_INCOMING) {
            respondToCall(callDetails, CallResponse.Builder().build())
            return
        }

        val number = callDetails.handle?.schemeSpecificPart
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
        respondToCall(callDetails, response.build())
    }
}
