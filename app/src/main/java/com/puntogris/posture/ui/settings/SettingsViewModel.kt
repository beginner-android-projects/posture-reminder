package com.puntogris.posture.ui.settings

import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.local.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDao: UserDao
): ViewModel() {

    suspend fun updateUserName(name: String) = userDao.updateUsername(name)

    suspend fun getUser() = userDao.getUser()

}