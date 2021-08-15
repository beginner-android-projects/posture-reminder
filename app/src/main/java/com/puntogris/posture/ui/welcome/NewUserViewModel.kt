package com.puntogris.posture.ui.welcome

import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.local.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(
    private val userDao: UserDao
):ViewModel() {

    suspend fun updateUsername(name: String) = userDao.updateUsername(name)

}