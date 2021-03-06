package com.puntogris.posture.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.Keep
import androidx.room.*
import org.jetbrains.annotations.NotNull
import com.puntogris.posture.R
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity
data class Reminder(

    @PrimaryKey(autoGenerate = false)
    val id: String = "",

    @ColumnInfo
    var name: String = "",

    @ColumnInfo
    val isActive: Boolean = false,

    @ColumnInfo
    var timeInterval: Int = 0,

    @ColumnInfo
    var startTime: Int = 0,

    @ColumnInfo
    var endTime: Int = 0,

    @ColumnInfo
    var alarmDays: List<Int> = listOf(),

    @ColumnInfo
    var color: Int = R.color.grey,

    @ColumnInfo
    var vibrationPattern: Int = 0,

    @ColumnInfo
    var soundUri: String = "",

    @ColumnInfo
    var soundName: String = ""
):Parcelable{

    fun alarmNotPastMidnight() = startTime < endTime

    fun alarmDaysSummary(daysList: Array<String>) =
         alarmDays.joinToString(", ") {
            daysList[it].first().toString()
         }

    fun timeIntervalSummary(): String{
        return if (timeInterval < 60){
            "$timeInterval m."
        }else{
            "${timeInterval/60} h. ${timeInterval % 60} m."
        }
    }

    fun alarmPastMidnightAndOutOfRange(minutesSinceMidnight: Int) =
       minutesSinceMidnight in (endTime + 1) until startTime

}