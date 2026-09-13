package com.pagrey.trashstopper.reputation

import android.content.Context
import androidx.room.withTransaction
import com.pagrey.trashstopper.data.AppDatabase
import com.pagrey.trashstopper.data.NumberEntity
import com.pagrey.trashstopper.screening.ScreeningRuntime

/** Replaces the external snapshot while preserving local user-report reputation. */
class ReputationSnapshotImporter(context: Context) {
    private val database = AppDatabase.getInstance(context)
    private val numbers = database.numberDao()
    private val reports = database.reportDao()

    suspend fun import(payload: ReputationSnapshotPayload) {
        val localReports = reports.all()
        val reportCounts = localReports.groupingBy { it.phoneNumber }.eachCount()
        val categories = localReports.groupBy { it.phoneNumber }
            .mapValues { (_, items) -> items.last().category }

        val merged = payload.numbers.map { number ->
            val count = reportCounts[number.phoneNumber] ?: 0
            if (count == 0) number else number.copy(
                category = categories[number.phoneNumber] ?: number.category,
                reportCount = number.reportCount + count,
                riskScore = (number.riskScore + (35 * count)).coerceAtMost(100)
            )
        }.toMutableList()

        val known = merged.mapTo(HashSet()) { it.phoneNumber }
        localReports.groupBy { it.phoneNumber }.forEach { (phone, items) ->
            if (phone !in known) {
                val category = items.last().category
                merged += NumberEntity(
                    phoneNumber = phone,
                    country = if (phone.startsWith("+34")) "ES" else null,
                    category = category,
                    riskScore = (35 * items.size).coerceAtMost(100),
                    reportCount = items.size,
                    verified = false
                )
            }
        }

        database.withTransaction {
            numbers.clear()
            numbers.upsertAll(merged)
        }

        ScreeningRuntime.cache.replaceAll(merged)
    }
}
