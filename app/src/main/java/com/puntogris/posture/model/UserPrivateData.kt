package com.puntogris.posture.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class UserPrivateData(
    @PrimaryKey(autoGenerate = false)
    val userId: String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val country: String = "",

    @ColumnInfo
    val email: String = "",

    @ColumnInfo
    val photoUrl: String = "",

    @ColumnInfo
    val creationDate: Timestamp = Timestamp.now(),

    @ColumnInfo
    val experience: Int = 0,

    @ColumnInfo
    val currentReminderId: String = ""
):Parcelable