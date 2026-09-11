package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.NumberEntity
import com.pagrey.trashstopper.data.RuleEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class RuleAwareScreeningEngineTest {
    @Test
    fun userAllowRuleOverridesHighReputationRisk() {
        val reputation = LocalReputationCache().apply {
            put(NumberEntity(phoneNumber = "+34900123456", riskScore = 60, reportCount = 1))
        }
        val rules = LocalRuleCache().apply {
            put(RuleEntity(phoneNumber = "+34900123456", action = "ALLOW"))
        }

        val result = RuleAwareScreeningEngine(reputation, rules).evaluate("+34 900 123 456")

        assertEquals(ScreeningDecision.Action.ALLOW, result.action)
        assertEquals("user-rule-allow", result.reason)
    }

    @Test
    fun userBlockRuleOverridesLowReputationRisk() {
        val reputation = LocalReputationCache().apply {
            put(NumberEntity(phoneNumber = "+34611222333", riskScore = 10))
        }
        val rules = LocalRuleCache().apply {
            put(RuleEntity(phoneNumber = "+34611222333", action = "BLOCK"))
        }

        val result = RuleAwareScreeningEngine(reputation, rules).evaluate("+34611222333")

        assertEquals(ScreeningDecision.Action.BLOCK, result.action)
        assertEquals("user-rule-block", result.reason)
    }

    @Test
    fun noUserRuleFallsBackToReputation() {
        val reputation = LocalReputationCache().apply {
            put(NumberEntity(phoneNumber = "+34910000000", riskScore = 50, reportCount = 1))
        }
        val rules = LocalRuleCache()

        val result = RuleAwareScreeningEngine(reputation, rules).evaluate("+34910000000")

        assertEquals(ScreeningDecision.Action.BLOCK, result.action)
        assertEquals(85, result.riskScore)
        assertEquals("riesgo-critico", result.reason)
    }

    @Test
    fun automaticProtectionDisabledAllowsReputationHit() {
        val reputation = LocalReputationCache().apply {
            put(NumberEntity(phoneNumber = "+34900123456", riskScore = 90))
        }
        val policy = ProtectionPolicy().apply { automaticProtection = false }

        val result = RuleAwareScreeningEngine(reputation, LocalRuleCache(), policy)
            .evaluate("+34900123456")

        assertEquals(ScreeningDecision.Action.ALLOW, result.action)
        assertEquals("automatic-protection-disabled", result.reason)
    }

    @Test
    fun unknownBlockingBlocksNumberWithoutLocalReputation() {
        val policy = ProtectionPolicy().apply { unknownBlocking = true }

        val result = RuleAwareScreeningEngine(
            LocalReputationCache(),
            LocalRuleCache(),
            policy
        ).evaluate("+34600000000")

        assertEquals(ScreeningDecision.Action.BLOCK, result.action)
        assertEquals("unknown-number-policy", result.reason)
    }

    @Test
    fun disabledSpamPolicyAllowsSpamReputation() {
        val reputation = LocalReputationCache().apply {
            put(NumberEntity(phoneNumber = "+34900123456", riskScore = 90, category = "SPAM"))
        }
        val policy = ProtectionPolicy().apply { spamBlocking = false }

        val result = RuleAwareScreeningEngine(reputation, LocalRuleCache(), policy)
            .evaluate("+34900123456")

        assertEquals(ScreeningDecision.Action.ALLOW, result.action)
        assertEquals(90, result.riskScore)
        assertEquals("category-policy-disabled", result.reason)
    }

    @Test
    fun disabledFraudPolicyAllowsScamReputation() {
        val reputation = LocalReputationCache().apply {
            put(NumberEntity(phoneNumber = "+34900123456", riskScore = 90, category = "FRAUD"))
        }
        val policy = ProtectionPolicy().apply { fraudBlocking = false }

        val result = RuleAwareScreeningEngine(reputation, LocalRuleCache(), policy)
            .evaluate("+34900123456")

        assertEquals(ScreeningDecision.Action.ALLOW, result.action)
        assertEquals(90, result.riskScore)
        assertEquals("category-policy-disabled", result.reason)
    }
}
