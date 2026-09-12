package com.pagrey.trashstopper

import android.app.Application
import com.pagrey.trashstopper.data.ProtectionPreferences
import com.pagrey.trashstopper.data.TrashStopperDataStore
import com.pagrey.trashstopper.reputation.ReputationSnapshotImporter
import com.pagrey.trashstopper.screening.ScreeningRuntime
import com.pagrey.trashstopper.sync.ReputationSyncRuntime
import com.pagrey.trashstopper.sync.ReputationSyncScheduler
import com.pagrey.trashstopper.sync.SpainSpamListProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TrashStopperApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        ProtectionPreferences(this).loadIntoRuntime()
        ReputationSyncRuntime.provider = SpainSpamListProvider()

        val store = TrashStopperDataStore(this)
        applicationScope.launch {
            store.warmReputationCache(ScreeningRuntime.cache)
            store.warmRuleCache(ScreeningRuntime.rules)
            try {
                val payload = SpainSpamListProvider().fetch(this@TrashStopperApplication)
                ReputationSnapshotImporter(this@TrashStopperApplication).import(payload)
            } catch (_: Exception) {
                // Keep the last local snapshot when the source is unavailable.
            }
        }
        ReputationSyncScheduler.schedule(this)
    }
}
