package com.pagrey.trashstopper.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reports",
    indices = [Index(value = ["phoneNumber", "category", "createdAt"], unique = true)]
)
data class ReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val category: String,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
