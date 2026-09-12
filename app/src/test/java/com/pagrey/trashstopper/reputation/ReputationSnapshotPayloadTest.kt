package com.pagrey.trashstopper.reputation

import com.pagrey.trashstopper.data.NumberEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class ReputationSnapshotPayloadTest {
    @Test
    fun preservesSnapshotVersionAndNumberRecords() {
        val number = NumberEntity(phoneNumber = "+34900111222", riskScore = 80)
        val payload = ReputationSnapshotPayload("2026-09-12", listOf(number))

        assertEquals("2026-09-12", payload.snapshotVersion)
        assertEquals(1, payload.numbers.size)
        assertEquals(number, payload.numbers.single())
    }
}
