package com.puntogris.posture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.puntogris.posture.model.DayHistory
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.UserPrivateData
import com.puntogris.posture.utils.Converters

@Database(entities = [
    Reminder::class,
    UserPrivateData::class,
    DayHistory::class
                     ], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
    abstract fun userDao(): UserDao
    abstract fun dayHistoryDao(): DayHistoryDao
}