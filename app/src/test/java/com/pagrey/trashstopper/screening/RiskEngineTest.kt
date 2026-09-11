package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.NumberEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class RiskEngineTest {
    @Test
    fun unknownNumberIsAllowed() {
        val result = RiskEngine.evaluate(null)
        assertEquals(0, result.score)
        assertEquals(ScreeningDecision.Action.ALLOW, result.action)
    }

    @Test
    fun criticalNumberIsBlocked() {
        val number = NumberEntity(phoneNumber = "+34900123456", riskScore = 50, reportCount = 1)
        val result = RiskEngine.evaluate(number)
        assertEquals(85, result.score)
        assertEquals(ScreeningDecision.Action.BLOCK, result.action)
    }

    @Test
    fun highRiskNumberIsSilenced() {
        val number = NumberEntity(phoneNumber = "+34611222333", riskScore = 20, reportCount = 1)
        val result = RiskEngine.evaluate(number)
        assertEquals(55, result.score)
        assertEquals(ScreeningDecision.Action.SILENCE, result.action)
    }

    @Test
    fun verifiedNumberGetsRiskReduction() {
        val number = NumberEntity(phoneNumber = "+34910000000", riskScore = 40, verified = true)
        val result = RiskEngine.evaluate(number)
        assertEquals(20, result.score)
        assertEquals(ScreeningDecision.Action.ALLOW, result.action)
    }
}
