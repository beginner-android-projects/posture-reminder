package com.puntogris.posture.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puntogris.posture.data.local.UserDao
import com.puntogris.posture.data.repo.login.LoginRepository
import com.puntogris.posture.utils.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDao: UserDao,
    private val dataStore: DataStore,
    private val loginRepository: LoginRepository
    ): ViewModel() {

    suspend fun updateUserName(name: String) = userDao.updateUsername(name)

    fun getUserFlow() = userDao.getUserFlow()

    fun getThemeNamePosition() = dataStore.appThemeFlow()

    fun setPandaAnimationPref(value:Boolean){
        viewModelScope.launch {
            dataStore.setPandaAnimation(value)
        }
    }

    fun logOut() = loginRepository.signOutUserFromFirebaseAndGoogle()
}