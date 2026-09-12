package com.pagrey.trashstopper.data

import java.io.File
import java.nio.file.Files
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SnapshotFileStoreTest {
    @Test
    fun activationKeepsPreviousSnapshotForRollback() {
        val dir = Files.createTempDirectory("trashstopper-snapshot").toFile()
        try {
            val store = SnapshotFileStore(dir)
            val first = "first".toByteArray()
            val second = "second".toByteArray()

            store.stage(first, SnapshotFileStore.sha256(first))
            assertTrue(store.activate())
            store.stage(second, SnapshotFileStore.sha256(second))
            assertTrue(store.activate())

            assertArrayEquals(second, store.readActive())
            assertTrue(store.rollback())
            assertArrayEquals(first, store.readActive())
        } finally {
            dir.deleteRecursively()
        }
    }

    @Test
    fun checksumMismatchDoesNotWriteStagingSnapshot() {
        val dir = Files.createTempDirectory("trashstopper-snapshot").toFile()
        try {
            val store = SnapshotFileStore(dir)
            val payload = "payload".toByteArray()
            runCatching { store.stage(payload, "bad-checksum") }
                .onSuccess { error("Expected checksum validation to fail") }
            assertFalse(File(dir, "snapshot.staging").exists())
        } finally {
            dir.deleteRecursively()
        }
    }
}
