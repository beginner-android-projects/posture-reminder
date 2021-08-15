package com.puntogris.posture.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.puntogris.posture.model.Reminder
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder):Long

    @Update
    suspend fun update(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("SELECT * FROM reminder INNER JOIN user ON currentReminderId = id WHERE id = 1")
    fun getReminderConfigLiveData(): LiveData<Reminder>

    @Query("SELECT * FROM reminder INNER JOIN user ON currentReminderId = id WHERE id = 1")
    fun getActiveReminder(): Reminder

    @Query("UPDATE reminder SET startTime = :startTime WHERE id = 1")
    suspend fun updateStartTime(startTime: Int)

    @Query("UPDATE reminder SET endTime = :endTime WHERE id = 1")
    suspend fun updateEndTime(endTime: Int)

    @Query("UPDATE reminder SET timeInterval = :timeInterval WHERE id = 1")
    suspend fun updateTimeInterval(timeInterval: Int)

    @Query("UPDATE reminder SET isActive = :isActive WHERE id = 1")
    suspend fun updateReminderStatus(isActive: Boolean)

    @Query("UPDATE reminder SET alarmDays = :days WHERE id = 1")
    suspend fun updateAlarmDays(days: List<Int>)

    @Query("SELECT * FROM reminder")
    fun getAllRemindersFlow(): LiveData<List<Reminder>>

}