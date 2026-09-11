package com.pagrey.trashstopper.data

import android.content.Context
import com.pagrey.trashstopper.screening.ScreeningRuntime

/** Local protection policy persisted independently from reputation data. */
class ProtectionPreferences(context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences(
        "trashstopper_protection",
        Context.MODE_PRIVATE
    )

    var spamBlocking: Boolean
        get() = preferences.getBoolean(KEY_SPAM, true)
        set(value) {
            preferences.edit().putBoolean(KEY_SPAM, value).apply()
            ScreeningRuntime.protection.spamBlocking = value
        }

    var fraudBlocking: Boolean
        get() = preferences.getBoolean(KEY_FRAUD, true)
        set(value) {
            preferences.edit().putBoolean(KEY_FRAUD, value).apply()
            ScreeningRuntime.protection.fraudBlocking = value
        }

    var unknownBlocking: Boolean
        get() = preferences.getBoolean(KEY_UNKNOWN, false)
        set(value) {
            preferences.edit().putBoolean(KEY_UNKNOWN, value).apply()
            ScreeningRuntime.protection.unknownBlocking = value
        }

    var automaticProtection: Boolean
        get() = preferences.getBoolean(KEY_AUTOMATIC, true)
        set(value) {
            preferences.edit().putBoolean(KEY_AUTOMATIC, value).apply()
            ScreeningRuntime.protection.automaticProtection = value
        }

    fun loadIntoRuntime() {
        ScreeningRuntime.protection.spamBlocking = spamBlocking
        ScreeningRuntime.protection.fraudBlocking = fraudBlocking
        ScreeningRuntime.protection.unknownBlocking = unknownBlocking
        ScreeningRuntime.protection.automaticProtection = automaticProtection
    }

    private companion object {
        const val KEY_SPAM = "spam_blocking"
        const val KEY_FRAUD = "fraud_blocking"
        const val KEY_UNKNOWN = "unknown_blocking"
        const val KEY_AUTOMATIC = "automatic_protection"
    }
}
