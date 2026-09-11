package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.NumberEntity

class RuleAwareScreeningEngine(
    private val reputationCache: LocalReputationCache = ScreeningRuntime.cache,
    private val ruleCache: LocalRuleCache = ScreeningRuntime.rules
) {
    fun evaluate(number: String?): ScreeningDecision {
        val normalized = PhoneNumberNormalizer.normalize(number)
        if (normalized.isBlank()) {
            return ScreeningDecision(ScreeningDecision.Action.ALLOW, reason = "unknown-number")
        }
        when (ruleCache.get(normalized)?.action?.uppercase()) {
            "BLOCK" -> return ScreeningDecision(ScreeningDecision.Action.BLOCK, reason = "user-rule-block")
            "ALLOW" -> return ScreeningDecision(ScreeningDecision.Action.ALLOW, reason = "user-rule-allow")
        }
        val reputation: NumberEntity? = reputationCache.get(normalized)
        val result = RiskEngine.evaluate(reputation)
        return ScreeningDecision(result.action, result.score, result.reason)
    }
}
