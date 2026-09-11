package com.pagrey.trashstopper.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CallEventDao {
    @Insert
    suspend fun insert(event: CallEventEntity)

    @Query("SELECT * FROM call_events ORDER BY timestamp DESC LIMIT :limit")
    suspend fun recent(limit: Int): List<CallEventEntity>
}
