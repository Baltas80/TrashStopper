package com.pagrey.trashstopper.data

import java.io.File
import java.security.MessageDigest

/** Atomic file boundary for keeping the last known-good data snapshot active. */
class SnapshotFileStore(private val directory: File) {
    private val active = File(directory, "snapshot.active")
    private val staging = File(directory, "snapshot.staging")
    private val previous = File(directory, "snapshot.previous")

    init { directory.mkdirs() }

    fun stage(bytes: ByteArray, expectedSha256: String) {
        check(sha256(bytes).equals(expectedSha256, ignoreCase = true)) { "Snapshot checksum mismatch" }
        staging.writeBytes(bytes)
    }

    fun activate(): Boolean {
        if (!staging.isFile) return false
        if (active.isFile) {
            if (previous.exists()) previous.delete()
            check(active.renameTo(previous)) { "Cannot preserve active snapshot" }
        }
        if (!staging.renameTo(active)) {
            if (previous.isFile && !active.exists()) previous.renameTo(active)
            return false
        }
        return true
    }

    fun rollback(): Boolean {
        if (!previous.isFile) return false
        if (active.exists()) active.delete()
        return previous.renameTo(active)
    }

    fun readActive(): ByteArray? = active.takeIf { it.isFile }?.readBytes()

    companion object {
        fun sha256(bytes: ByteArray): String = MessageDigest.getInstance("SHA-256")
            .digest(bytes)
            .joinToString("") { "%02x".format(it) }
    }
}
