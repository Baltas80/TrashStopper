package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.NumberEntity

object RiskEngine {
    data class Result(val score: Int, val reason: String, val action: ScreeningDecision.Action)

    fun evaluate(number: NumberEntity?): Result {
        if (number == null) {
            return Result(0, "sin-reputacion-local", ScreeningDecision.Action.ALLOW)
        }

        var score = number.riskScore.coerceIn(0, 100)
        if (number.reportCount > 0) score = (score + 35).coerceAtMost(100)
        if (number.verified) score = (score - 20).coerceAtLeast(0)

        val action = when {
            score >= 75 -> ScreeningDecision.Action.BLOCK
            score >= 50 -> ScreeningDecision.Action.SILENCE
            else -> ScreeningDecision.Action.ALLOW
        }
        val reason = when {
            number.verified -> "numero-verificado"
            score >= 75 -> "riesgo-critico"
            score >= 50 -> "riesgo-alto"
            score >= 25 -> "riesgo-moderado"
            else -> "riesgo-bajo"
        }
        return Result(score, reason, action)
    }
}
