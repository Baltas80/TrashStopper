package com.pagrey.trashstopper.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NumberDao {
    @Query("SELECT * FROM numbers WHERE phoneNumber = :phoneNumber LIMIT 1")
    suspend fun find(phoneNumber: String): NumberEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(number: NumberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(numbers: List<NumberEntity>)

    @Query("SELECT * FROM numbers")
    suspend fun getAll(): List<NumberEntity>

    @Query("DELETE FROM numbers")
    suspend fun clear()
}
