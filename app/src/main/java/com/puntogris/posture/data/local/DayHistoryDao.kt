package com.puntogris.posture.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.puntogris.posture.model.DayHistory
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset

@Dao
interface DayHistoryDao {

    @Insert
    suspend fun insert(dayHistory: DayHistory)

    @Update
    suspend fun update(dayHistory: DayHistory)

    @Query("SELECT * from dayhistory ORDER BY date DESC LIMIT 1")
    suspend fun getLastEntry(): DayHistory?

    @Query("SELECT * from dayhistory WHERE date >= (SELECT date('now', '-2 day')) ORDER BY date DESC LIMIT 2")
    fun getLastTwoEntries(): LiveData<List<DayHistory>>

    @Query("SELECT * from dayhistory WHERE date >= (SELECT date('now', '-7 day')) ORDER BY date DESC LIMIT 7")
    suspend fun getWeekEntries(): List<DayHistory>

    @Transaction
    suspend fun insertData(dayHistory: DayHistory){
        val today = LocalDate.now().toString()
        val lastEntry = getLastEntry()
        if (lastEntry == null || lastEntry.date != today){
            insert(dayHistory)
        }else{
            //update
        }
    }
}