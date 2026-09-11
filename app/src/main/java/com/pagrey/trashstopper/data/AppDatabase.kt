package com.pagrey.trashstopper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NumberEntity::class, RuleEntity::class, CallEventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun numberDao(): NumberDao
    abstract fun ruleDao(): RuleDao
    abstract fun callEventDao(): CallEventDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "trashstopper.db"
                ).build().also { INSTANCE = it }
            }
    }
}
