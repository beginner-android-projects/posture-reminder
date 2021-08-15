package com.puntogris.posture.ui.account

import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.local.DayHistoryDao
import com.puntogris.posture.data.local.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userDao: UserDao,
    private val dayHistoryDao: DayHistoryDao
): ViewModel() {

    val user = userDao.getUserLiveData()

    suspend fun getWeekData() = dayHistoryDao.getWeekEntries()


}