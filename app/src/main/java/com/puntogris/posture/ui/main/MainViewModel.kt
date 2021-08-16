package com.puntogris.posture.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puntogris.posture.BuildConfig
import com.puntogris.posture.model.AuthState
import com.puntogris.posture.utils.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val  dataStore: DataStore
):ViewModel() {

    init {
        viewModelScope.launch {
            if (dataStore.lastVersionCode() < BuildConfig.VERSION_CODE) {
                dataStore.updateLastVersionCode()
                _appVersionStatus.value = true
            }
        }
    }

    private val _appVersionStatus = MutableLiveData<Boolean>()
    val appVersionStatus: LiveData<Boolean>
        get() = _appVersionStatus


    fun isUserLoggedIn(): AuthState {
//        return if (repository.restoreLoggedUser() == null) AuthState.AuthRequired
//        else AuthState.AuthComplete
        return AuthState.AuthRequired
    }
}