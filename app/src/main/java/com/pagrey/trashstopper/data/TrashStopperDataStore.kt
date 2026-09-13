package com.pagrey.trashstopper.data

import android.content.Context
import androidx.room.withTransaction
import com.pagrey.trashstopper.screening.LocalReputationCache
import com.pagrey.trashstopper.screening.LocalRuleCache
import com.pagrey.trashstopper.screening.PhoneNumberNormalizer
import com.pagrey.trashstopper.screening.ScreeningRuntime

class TrashStopperDataStore(context: Context) {
    private val database = AppDatabase.getInstance(context)
    private val numbers = database.numberDao()
    private val rules = database.ruleDao()
    private val events = database.callEventDao()
    private val reports = database.reportDao()

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

    suspend fun saveReport(report: ReportEntity): Boolean = reports.insert(report) != -1L

    suspend fun recentReports(limit: Int = 50): List<ReportEntity> = reports.recent(limit)

    suspend fun submitReport(phoneNumber: String, category: String, note: String?): Boolean {
        val normalized = PhoneNumberNormalizer.normalize(phoneNumber)
        if (normalized.isBlank()) return false

        val now = System.currentTimeMillis()
        val updated = database.withTransaction {
            val existing = numbers.find(normalized)
            val baseRisk = when (category) {
                "FRAUDE" -> 50
                "SPAM", "ROBOCALL" -> 35
                "TELEMARKETING" -> 25
                else -> 20
            }
            val report = ReportEntity(
                phoneNumber = normalized,
                category = category,
                note = note?.trim()?.takeIf { it.isNotEmpty() },
                createdAt = now
            )
            val reportId = reports.insert(report)
            if (reportId == -1L) return@withTransaction null

            val entity = if (existing == null) {
                NumberEntity(
                    phoneNumber = normalized,
                    country = if (normalized.length == 9) "ES" else null,
                    category = category,
                    riskScore = baseRisk,
                    reportCount = 1,
                    verified = false,
                    lastReportedAt = now,
                    updatedAt = now
                )
            } else {
                existing.copy(
                    category = if (existing.category == "FRAUDE" || category == "FRAUDE") "FRAUDE" else category,
                    riskScore = maxOf(existing.riskScore, baseRisk),
                    reportCount = existing.reportCount + 1,
                    lastReportedAt = now,
                    updatedAt = now
                )
            }
            numbers.upsert(entity)
            entity
        }
        updated ?: return false
        ScreeningRuntime.cache.put(updated)
        return true
    }
}
