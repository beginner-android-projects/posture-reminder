package com.puntogris.posture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.puntogris.posture.model.DayHistory

@Database(entities = [
    DayHistory::class
                     ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayHistoryDao(): DayHistoryDao
}