package com.pagrey.trashstopper.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pagrey.trashstopper.reputation.ReputationSnapshotImporter
import com.pagrey.trashstopper.reputation.ReputationSnapshotPayload

class ReputationSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val provider = SpainSpamListProvider()
        return try {
            val payload = provider.fetch(applicationContext)
            ReputationSnapshotImporter(applicationContext).import(payload)
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}

fun interface ReputationSnapshotProvider {
    suspend fun fetch(context: Context): ReputationSnapshotPayload
}

object ReputationSyncRuntime {
    @Volatile
    var provider: ReputationSnapshotProvider? = null
}
