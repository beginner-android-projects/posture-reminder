package com.puntogris.posture.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Keep
@Entity
data class DayHistory(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    val expGained: Int = 0,

    @ColumnInfo
    val notifications: Int = 0,

    @ColumnInfo
    val exercises: Int = 0,

    @ColumnInfo
    val date: String = LocalDate.now().toString()
)