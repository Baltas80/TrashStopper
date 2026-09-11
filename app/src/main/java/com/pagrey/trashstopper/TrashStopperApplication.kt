package com.pagrey.trashstopper

import android.app.Application
import com.pagrey.trashstopper.data.ProtectionPreferences
import com.pagrey.trashstopper.data.TrashStopperDataStore
import com.pagrey.trashstopper.screening.ScreeningRuntime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TrashStopperApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        ProtectionPreferences(this).loadIntoRuntime()
        val store = TrashStopperDataStore(this)
        applicationScope.launch {
            store.warmReputationCache(ScreeningRuntime.cache)
            store.warmRuleCache(ScreeningRuntime.rules)
        }
    }
}
