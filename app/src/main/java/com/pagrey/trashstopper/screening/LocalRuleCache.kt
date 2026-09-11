package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.RuleEntity
import java.util.concurrent.ConcurrentHashMap

class LocalRuleCache {
    private val entries = ConcurrentHashMap<String, RuleEntity>()

    fun put(rule: RuleEntity) { entries[rule.phoneNumber] = rule }
    fun putAll(rules: List<RuleEntity>) { rules.forEach(::put) }
    fun get(phoneNumber: String): RuleEntity? = entries[phoneNumber]
    fun remove(phoneNumber: String) { entries.remove(phoneNumber) }
    fun clear() = entries.clear()
}
