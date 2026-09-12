package com.pagrey.trashstopper.reputation

import java.security.MessageDigest

/** Pure validation logic kept independent from Android and networking. */
object ReputationSnapshotValidator {
    fun validate(metadata: ReputationSnapshotMetadata, content: ByteArray): Boolean {
        if (metadata.version.isBlank()) return false
        if (metadata.generatedAtEpochMs <= 0L) return false
        if (metadata.recordCount < 0L) return false
        if (metadata.schemaVersion <= 0) return false

        val expected = metadata.contentSha256.lowercase()
        if (!expected.matches(Regex("[0-9a-f]{64}"))) return false

        return sha256(content) == expected
    }

    private fun sha256(content: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(content)
        return digest.joinToString("") { "%02x".format(it) }
    }
}
