package com.pagrey.trashstopper.data

class NumberRepository(private val dao: NumberDao) {
    suspend fun find(phoneNumber: String): NumberEntity? = dao.find(phoneNumber)
    suspend fun upsert(number: NumberEntity) = dao.upsert(number)
    suspend fun all(): List<NumberEntity> = dao.getAll()
    suspend fun clear() = dao.clear()
}
