package com.pagrey.trashstopper.reputation

import android.content.Context
import androidx.room.withTransaction
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

        // Publish the new in-memory snapshot in one atomic operation. Never clear
        // the cache first: the screening service may be invoked at any time.
        ScreeningRuntime.cache.replaceAll(payload.numbers)
    }
}
