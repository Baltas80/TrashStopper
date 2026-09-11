package com.pagrey.trashstopper.screening

/** Fast, local decision returned by the screening hot path. */
data class ScreeningDecision(
    val action: Action,
    val riskScore: Int = 0,
    val reason: String = ""
) {
    enum class Action { ALLOW, SILENCE, BLOCK }
}
