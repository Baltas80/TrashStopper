package com.pagrey.trashstopper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NumberEntity::class], version = 1, exportSchema = false)
abstract class TrashStopperDatabase : RoomDatabase() {
    abstract fun numberDao(): NumberDao

    companion object {
        @Volatile private var INSTANCE: TrashStopperDatabase? = null

        fun get(context: Context): TrashStopperDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TrashStopperDatabase::class.java,
                    "trashstopper.db"
                ).build().also { INSTANCE = it }
            }
    }
}
