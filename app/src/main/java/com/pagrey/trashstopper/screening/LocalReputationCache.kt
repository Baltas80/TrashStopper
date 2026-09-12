package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.NumberEntity
import java.util.concurrent.atomic.AtomicReference

/**
 * In-memory reputation snapshot used by the synchronous screening path.
 *
 * Snapshot replacement is atomic: screening sees either the previous complete
 * snapshot or the new complete snapshot, never an intermediate/empty state.
 */
class LocalReputationCache {
    private val snapshot = AtomicReference<Map<String, NumberEntity>>(emptyMap())

    fun put(number: NumberEntity) {
        while (true) {
            val current = snapshot.get()
            val updated = current + (number.phoneNumber to number)
            if (snapshot.compareAndSet(current, updated)) return
        }
    }

    fun putAll(numbers: List<NumberEntity>) {
        if (numbers.isEmpty()) return
        while (true) {
            val current = snapshot.get()
            val updated = current + numbers.associateBy { it.phoneNumber }
            if (snapshot.compareAndSet(current, updated)) return
        }
    }

    /** Atomically replaces the entire reputation snapshot. */
    fun replaceAll(numbers: List<NumberEntity>) {
        snapshot.set(numbers.associateBy { it.phoneNumber })
    }

    fun get(phoneNumber: String): NumberEntity? = snapshot.get()[phoneNumber]

    fun clear() {
        snapshot.set(emptyMap())
    }
}
