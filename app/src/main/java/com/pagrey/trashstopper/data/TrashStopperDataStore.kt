package com.pagrey.trashstopper.data

import android.content.Context
import com.pagrey.trashstopper.screening.LocalReputationCache
import com.pagrey.trashstopper.screening.LocalRuleCache
import com.pagrey.trashstopper.screening.ScreeningRuntime

class TrashStopperDataStore(context: Context) {
    private val database = AppDatabase.getInstance(context)
    private val numbers = database.numberDao()
    private val rules = database.ruleDao()
    private val events = database.callEventDao()

    suspend fun warmReputationCache(cache: LocalReputationCache) {
        cache.putAll(numbers.getAll())
    }

    suspend fun warmRuleCache(cache: LocalRuleCache) {
        cache.putAll(rules.getAll())
    }

    suspend fun findNumber(phoneNumber: String): NumberEntity? = numbers.find(phoneNumber)

    suspend fun saveNumber(number: NumberEntity) {
        numbers.upsert(number)
        ScreeningRuntime.cache.put(number)
    }

    suspend fun saveRule(rule: RuleEntity) {
        rules.upsert(rule)
        ScreeningRuntime.rules.put(rule)
    }

    suspend fun deleteRule(phoneNumber: String) {
        rules.delete(phoneNumber)
        ScreeningRuntime.rules.remove(phoneNumber)
    }

    suspend fun getRule(phoneNumber: String): RuleEntity? = rules.find(phoneNumber)

    suspend fun getAllRules(): List<RuleEntity> = rules.getAll()

    suspend fun saveCallEvent(event: CallEventEntity) {
        events.insert(event)
    }

    suspend fun recentCallEvents(limit: Int = 50): List<CallEventEntity> = events.recent(limit)
}
