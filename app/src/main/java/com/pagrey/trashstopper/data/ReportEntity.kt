package com.pagrey.trashstopper.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val category: String,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
