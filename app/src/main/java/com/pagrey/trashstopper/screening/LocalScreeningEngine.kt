package com.pagrey.trashstopper.screening

/**
 * Synchronous first-pass engine for the Telecom 5-second screening boundary.
 * It only reads the in-memory cache; Room/network work stays outside this path.
 */
class LocalScreeningEngine(
    private val cache: LocalReputationCache = ScreeningRuntime.cache
) {
    fun evaluate(number: String?): ScreeningDecision {
        val normalized = PhoneNumberNormalizer.normalize(number)
        if (normalized.isBlank()) {
            return ScreeningDecision(ScreeningDecision.Action.ALLOW, reason = "unknown-number")
        }

        val reputation = cache.get(normalized)
        val result = RiskEngine.evaluate(reputation)
        return ScreeningDecision(
            action = result.action,
            riskScore = result.score,
            reason = result.reason
        )
    }
}
