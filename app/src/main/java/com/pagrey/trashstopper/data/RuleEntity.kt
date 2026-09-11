package com.pagrey.trashstopper.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_rules", indices = [Index(value = ["phoneNumber"], unique = true)])
data class RuleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val action: String,
    val createdAt: Long = System.currentTimeMillis()
)
