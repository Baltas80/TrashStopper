package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.NumberEntity

class RuleAwareScreeningEngine(
    private val reputationCache: LocalReputationCache = ScreeningRuntime.cache,
    private val ruleCache: LocalRuleCache = ScreeningRuntime.rules,
    private val policy: ProtectionPolicy = ScreeningRuntime.protection
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

        if (!policy.automaticProtection) {
            return ScreeningDecision(ScreeningDecision.Action.ALLOW, reason = "automatic-protection-disabled")
        }

        val reputation: NumberEntity? = reputationCache.get(normalized)
        if (reputation == null) {
            return if (policy.unknownBlocking) {
                ScreeningDecision(ScreeningDecision.Action.BLOCK, reason = "unknown-number-policy")
            } else {
                ScreeningDecision(ScreeningDecision.Action.ALLOW, reason = "sin-reputacion-local")
            }
        }

        val result = RiskEngine.evaluate(reputation)
        val category = reputation.category?.uppercase()
        val categoryEnabled = when {
            category == "FRAUD" || category == "SCAM" -> policy.fraudBlocking
            category == "SPAM" || category == "TELEMARKETING" || category == "ROBOCALL" -> policy.spamBlocking
            else -> true
        }

        if (!categoryEnabled) {
            return ScreeningDecision(
                action = ScreeningDecision.Action.ALLOW,
                riskScore = result.score,
                reason = "category-policy-disabled"
            )
        }

        return ScreeningDecision(result.action, result.score, result.reason)
    }
}
