package com.pagrey.trashstopper.sync

import org.junit.Assert.assertEquals
import org.junit.Test

class ReputationSyncSchedulerTest {
    @Test
    fun workNameIsStable() {
        assertEquals("trashstopper-reputation-sync", ReputationSyncScheduler.workNameForTests())
    }
}
