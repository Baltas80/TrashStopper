package com.pagrey.trashstopper.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pagrey.trashstopper.reputation.ReputationSnapshotImporter
import com.pagrey.trashstopper.reputation.ReputationSnapshotPayload

/** Background sync boundary. Network providers are injected separately from call screening. */
class ReputationSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val provider = ReputationSyncRuntime.provider ?: return Result.failure()
        return runCatching {
            val payload = provider.fetch(applicationContext)
            ReputationSnapshotImporter(applicationContext).import(payload)
        }.fold(
            onSuccess = { Result.success() },
            onFailure = { Result.retry() }
        )
    }
}

fun interface ReputationSnapshotProvider {
    suspend fun fetch(context: Context): ReputationSnapshotPayload
}

object ReputationSyncRuntime {
    @Volatile
    var provider: ReputationSnapshotProvider? = null
}
