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
}
