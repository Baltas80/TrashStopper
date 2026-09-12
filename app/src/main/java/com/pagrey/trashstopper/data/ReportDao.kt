package com.pagrey.trashstopper.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReportDao {
    @Insert
    suspend fun insert(report: ReportEntity)

    @Query("SELECT * FROM reports ORDER BY createdAt DESC LIMIT :limit")
    suspend fun recent(limit: Int = 50): List<ReportEntity>
}
