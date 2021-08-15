package com.puntogris.posture.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(

    @PrimaryKey(autoGenerate = false)
    val userId: Int = 1,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val level: Int,

    @ColumnInfo
    val experience: Int,

    @ColumnInfo
    val currentReminderId: Int
)