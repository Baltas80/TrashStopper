package com.pagrey.trashstopper.reputation

import androidx.room.withTransaction
import android.content.Context
import com.pagrey.trashstopper.data.AppDatabase
import com.pagrey.trashstopper.screening.ScreeningRuntime

/** Replaces reputation data only after the incoming snapshot has already been validated. */
class ReputationSnapshotImporter(context: Context) {
    private val database = AppDatabase.getInstance(context)
    private val numbers = database.numberDao()

    suspend fun import(payload: ReputationSnapshotPayload) {
        database.withTransaction {
            numbers.clear()
            payload.numbers.forEach { numbers.upsert(it) }
        }
        ScreeningRuntime.cache.clear()
        ScreeningRuntime.cache.putAll(numbers.getAll())
    }
}
