package com.pagrey.trashstopper.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_events")
data class CallEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val timestamp: Long = System.currentTimeMillis(),
    val result: String,
    val riskScore: Int,
    val action: String
)
