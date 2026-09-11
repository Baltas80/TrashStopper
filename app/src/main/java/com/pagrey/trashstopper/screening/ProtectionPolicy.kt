package com.pagrey.trashstopper.screening

/** In-memory policy used by the synchronous call-screening decision path. */
class ProtectionPolicy {
    @Volatile var automaticProtection: Boolean = true
    @Volatile var spamBlocking: Boolean = true
    @Volatile var fraudBlocking: Boolean = true
    @Volatile var unknownBlocking: Boolean = false
}
