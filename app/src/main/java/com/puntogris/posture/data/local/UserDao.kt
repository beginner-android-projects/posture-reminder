package com.puntogris.posture.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

//@Dao
//interface UserDao {
//
//    @Query("SELECT * FROM user WHERE userId = 1")
//    suspend fun getUser(): User
//
//    @Query("SELECT * FROM user WHERE userId = 1")
//    fun getUserLiveData(): LiveData<User>
//
//    @Query("UPDATE user SET currentReminderId = :reminderId WHERE userId = 1")
//    suspend fun updateCurrentUserReminder(reminderId: Int)
//
//    @Query("UPDATE user SET name = :name WHERE userId = 1")
//    suspend fun updateUsername(name: String)
//}