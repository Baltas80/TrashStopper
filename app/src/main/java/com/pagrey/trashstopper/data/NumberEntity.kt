package com.pagrey.trashstopper.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "numbers", indices = [Index(value = ["phoneNumber"], unique = true)])
data class NumberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val country: String? = null,
    val category: String? = null,
    val riskScore: Int = 0,
    val reportCount: Int = 0,
    val verified: Boolean = false,
    val lastReportedAt: Long? = null,
    val updatedAt: Long = System.currentTimeMillis()
)
