package com.pagrey.trashstopper.reputation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReputationSnapshotValidatorTest {
    @Test
    fun validSnapshotIsAccepted() {
        val content = "snapshot-v1".toByteArray()
        val metadata = ReputationSnapshotMetadata(
            version = "2026.09.12-001",
            generatedAtEpochMs = 1L,
            contentSha256 = sha256(content),
            recordCount = 1L,
            schemaVersion = 1
        )

        assertTrue(ReputationSnapshotValidator.validate(metadata, content))
    }

    @Test
    fun modifiedContentIsRejected() {
        val content = "snapshot-v1".toByteArray()
        val metadata = ReputationSnapshotMetadata(
            version = "2026.09.12-001",
            generatedAtEpochMs = 1L,
            contentSha256 = sha256(content),
            recordCount = 1L,
            schemaVersion = 1
        )

        assertFalse(ReputationSnapshotValidator.validate(metadata, "tampered".toByteArray()))
    }

    @Test
    fun malformedHashIsRejected() {
        val metadata = ReputationSnapshotMetadata(
            version = "2026.09.12-001",
            generatedAtEpochMs = 1L,
            contentSha256 = "not-a-sha256",
            recordCount = 0L,
            schemaVersion = 1
        )

        assertFalse(ReputationSnapshotValidator.validate(metadata, ByteArray(0)))
    }

    private fun sha256(content: ByteArray): String {
        val digest = java.security.MessageDigest.getInstance("SHA-256").digest(content)
        return digest.joinToString("") { "%02x".format(it) }
    }
}
