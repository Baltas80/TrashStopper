package com.pagrey.trashstopper.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(report: ReportEntity): Long

    @Query("SELECT EXISTS(SELECT 1 FROM reports WHERE phoneNumber = :phoneNumber AND category = :category AND createdAt = :createdAt)")
    suspend fun exists(phoneNumber: String, category: String, createdAt: Long): Boolean

    @Query("SELECT * FROM reports ORDER BY createdAt DESC LIMIT :limit")
    suspend fun recent(limit: Int = 50): List<ReportEntity>

    @Query("SELECT * FROM reports ORDER BY createdAt ASC")
    suspend fun all(): List<ReportEntity>
}
