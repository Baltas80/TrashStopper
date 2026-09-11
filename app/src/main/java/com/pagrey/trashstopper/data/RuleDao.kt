package com.pagrey.trashstopper.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RuleDao {
    @Query("SELECT * FROM user_rules WHERE phoneNumber = :phoneNumber LIMIT 1")
    suspend fun find(phoneNumber: String): RuleEntity?

    @Query("SELECT * FROM user_rules")
    suspend fun getAll(): List<RuleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(rule: RuleEntity)

    @Query("DELETE FROM user_rules WHERE phoneNumber = :phoneNumber")
    suspend fun delete(phoneNumber: String)
}
