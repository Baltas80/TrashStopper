package com.pagrey.trashstopper.screening

import com.pagrey.trashstopper.data.NumberEntity
import java.util.concurrent.ConcurrentHashMap

class LocalReputationCache {
    private val entries = ConcurrentHashMap<String, NumberEntity>()

    fun put(number: NumberEntity) {
        entries[number.phoneNumber] = number
    }

    fun putAll(numbers: List<NumberEntity>) {
        numbers.forEach(::put)
    }

    fun get(phoneNumber: String): NumberEntity? = entries[phoneNumber]

    fun clear() = entries.clear()
}
