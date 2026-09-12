package com.pagrey.trashstopper.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/** Background sync boundary. Network providers are injected separately from call screening. */
class ReputationSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val provider = ReputationSyncRuntime.provider
            ?: return Result.failure()
        return runCatching { provider.sync(applicationContext) }
            .fold(
                onSuccess = { Result.success() },
                onFailure = { Result.retry() }
            )
    }
}

fun interface ReputationSnapshotProvider {
    suspend fun sync(context: Context)
}

object ReputationSyncRuntime {
    @Volatile
    var provider: ReputationSnapshotProvider? = null
}
