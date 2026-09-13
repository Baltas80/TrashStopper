package com.pagrey.trashstopper.reputation

import android.content.Context
import androidx.room.withTransaction
import com.pagrey.trashstopper.data.AppDatabase
import com.pagrey.trashstopper.data.NumberEntity
import com.pagrey.trashstopper.screening.PhoneNumberNormalizer
import com.pagrey.trashstopper.screening.ScreeningRuntime

/** Replaces the external snapshot while preserving local user-report reputation. */
class ReputationSnapshotImporter(context: Context) {
    private val database = AppDatabase.getInstance(context)
    private val numbers = database.numberDao()
    private val reports = database.reportDao()

    suspend fun import(payload: ReputationSnapshotPayload) {
        val localReports = reports.all()
        val reportCounts = localReports.groupingBy { it.phoneNumber }.eachCount()
        val latestReports = localReports.groupBy { it.phoneNumber }
            .mapValues { (_, items) -> items.maxByOrNull { it.createdAt } }

        val externalNumbers = payload.numbers
            .mapNotNull { number ->
                val normalized = PhoneNumberNormalizer.normalize(number.phoneNumber)
                normalized.takeIf { it.isNotBlank() }?.let { number.copy(phoneNumber = it) }
            }
            .groupBy { it.phoneNumber }
            .map { (_, duplicates) ->
                duplicates.reduce { first, second ->
                    first.copy(
                        category = mergeCategory(first.category, second.category),
                        riskScore = maxOf(first.riskScore, second.riskScore).coerceIn(0, 100),
                        reportCount = maxOf(first.reportCount, second.reportCount),
                        verified = first.verified || second.verified,
                        lastReportedAt = maxOf(first.lastReportedAt ?: 0L, second.lastReportedAt ?: 0L)
                            .takeIf { it > 0L },
                        updatedAt = maxOf(first.updatedAt, second.updatedAt)
                    )
                }
            }

        val merged = externalNumbers.map { number ->
            val count = reportCounts[number.phoneNumber] ?: 0
            val latest = latestReports[number.phoneNumber]
            if (count == 0) number else number.copy(
                category = mergeCategory(number.category, latest?.category),
                reportCount = number.reportCount + count,
                riskScore = (number.riskScore + localRiskIncrement(latest?.category, count)).coerceAtMost(100),
                lastReportedAt = maxOf(number.lastReportedAt ?: 0L, latest?.createdAt ?: 0L)
                    .takeIf { it > 0L },
                updatedAt = maxOf(number.updatedAt, latest?.createdAt ?: 0L)
            )
        }.toMutableList()

        val known = merged.mapTo(HashSet()) { it.phoneNumber }
        localReports.groupBy { it.phoneNumber }.forEach { (phone, items) ->
            val normalized = PhoneNumberNormalizer.normalize(phone)
            if (normalized.isBlank() || normalized in known) return@forEach

            val latest = items.maxByOrNull { it.createdAt }
            val category = latest?.category
            merged += NumberEntity(
                phoneNumber = normalized,
                country = if (normalized.startsWith("+34")) "ES" else null,
                category = category,
                riskScore = localRiskIncrement(category, items.size).coerceAtMost(100),
                reportCount = items.size,
                verified = false,
                lastReportedAt = latest?.createdAt,
                updatedAt = latest?.createdAt ?: System.currentTimeMillis()
            )
            known += normalized
        }

        database.withTransaction {
            numbers.clear()
            numbers.upsertAll(merged)
        }

        ScreeningRuntime.cache.replaceAll(merged)
    }

    private fun localRiskIncrement(category: String?, count: Int): Int {
        val perReport = when (category) {
            "FRAUDE" -> 50
            "SPAM", "ROBOCALL" -> 35
            "TELEMARKETING" -> 25
            else -> 20
        }
        return perReport * count
    }

    private fun mergeCategory(first: String?, second: String?): String? = when {
        first == "FRAUDE" || second == "FRAUDE" -> "FRAUDE"
        second != null -> second
        else -> first
    }
}
