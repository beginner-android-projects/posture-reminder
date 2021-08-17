package com.puntogris.posture.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(

    @PrimaryKey(autoGenerate = false)
    val userId: String = "",

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val experienceCounter: Int,

    @ColumnInfo
    val currentReminderId: Int
)