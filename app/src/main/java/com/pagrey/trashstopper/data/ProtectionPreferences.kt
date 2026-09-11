package com.pagrey.trashstopper.data

import android.content.Context

/** Local protection policy persisted independently from reputation data. */
class ProtectionPreferences(context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences(
        "trashstopper_protection",
        Context.MODE_PRIVATE
    )

    var spamBlocking: Boolean
        get() = preferences.getBoolean(KEY_SPAM, true)
        set(value) = preferences.edit().putBoolean(KEY_SPAM, value).apply()

    var fraudBlocking: Boolean
        get() = preferences.getBoolean(KEY_FRAUD, true)
        set(value) = preferences.edit().putBoolean(KEY_FRAUD, value).apply()

    var unknownBlocking: Boolean
        get() = preferences.getBoolean(KEY_UNKNOWN, false)
        set(value) = preferences.edit().putBoolean(KEY_UNKNOWN, value).apply()

    var automaticProtection: Boolean
        get() = preferences.getBoolean(KEY_AUTOMATIC, true)
        set(value) = preferences.edit().putBoolean(KEY_AUTOMATIC, value).apply()

    private companion object {
        const val KEY_SPAM = "spam_blocking"
        const val KEY_FRAUD = "fraud_blocking"
        const val KEY_UNKNOWN = "unknown_blocking"
        const val KEY_AUTOMATIC = "automatic_protection"
    }
}
