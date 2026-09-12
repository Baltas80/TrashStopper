package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.NumberEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LocalReputationCacheTest {
    @Test
    fun replaceAllPublishesNewSnapshotWithoutClearingPreviousFirst() {
        val cache = LocalReputationCache()
        val oldNumber = NumberEntity(phoneNumber = "+34910000000", riskScore = 40)
        val newNumber = NumberEntity(phoneNumber = "+34910000001", riskScore = 90)

        cache.put(oldNumber)
        assertEquals(oldNumber, cache.get(oldNumber.phoneNumber))

        cache.replaceAll(listOf(newNumber))

        assertNull(cache.get(oldNumber.phoneNumber))
        assertEquals(newNumber, cache.get(newNumber.phoneNumber))
    }

    @Test
    fun emptyReplacementIsExplicitAndAtomic() {
        val cache = LocalReputationCache()
        val number = NumberEntity(phoneNumber = "+34910000002", riskScore = 80)
        cache.put(number)

        cache.replaceAll(emptyList())

        assertNull(cache.get(number.phoneNumber))
    }

    @Test
    fun putAddsToCurrentSnapshot() {
        val cache = LocalReputationCache()
        val first = NumberEntity(phoneNumber = "+34910000003", riskScore = 20)
        val second = NumberEntity(phoneNumber = "+34910000004", riskScore = 70)

        cache.put(first)
        cache.put(second)

        assertEquals(first, cache.get(first.phoneNumber))
        assertEquals(second, cache.get(second.phoneNumber))
    }
}
