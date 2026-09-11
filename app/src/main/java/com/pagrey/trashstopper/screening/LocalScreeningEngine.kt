package com.pagrey.trashstopper.screening

/**
 * Deterministic first-pass engine. Remote reputation must never be required here.
 * The richer reputation repository will be injected in a later layer.
 */
class LocalScreeningEngine {
    fun evaluate(number: String?): ScreeningDecision {
        val normalized = number?.filter { it.isDigit() || it == '+' }.orEmpty()
        if (normalized.isBlank()) {
            return ScreeningDecision(ScreeningDecision.Action.ALLOW, reason = "unknown-number")
        }
        return ScreeningDecision(ScreeningDecision.Action.ALLOW, reason = "no-local-match")
    }
}
