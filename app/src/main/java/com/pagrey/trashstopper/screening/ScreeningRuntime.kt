package com.pagrey.trashstopper.screening

object ScreeningRuntime {
    val cache: LocalReputationCache = LocalReputationCache()
    val rules: LocalRuleCache = LocalRuleCache()
}
