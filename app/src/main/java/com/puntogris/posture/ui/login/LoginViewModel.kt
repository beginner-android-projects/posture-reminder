package com.puntogris.posture.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    private val _loginLiveData = MutableLiveData<LoginResult>()
    val loginLiveData: LiveData<LoginResult>
        get() = _loginLiveData

    fun login(userName: String, password: String) {
        repository.login(
            userName = userName,
            password = password,
            loginSuccess = {
                Log.d("Login", "Logged in as $it")
             //   _progressLiveData.value = Event(false)
                _loginLiveData.value = LoginResult.LoginSuccess
            },
            loginError = {
                Log.d("Login", "Cannot log in. Error ${it?.errorMessage}")
            //    _progressLiveData.value = Event(false)
                _loginLiveData.value = LoginResult.LoginError("error")
            }
        )

      //  _progressLiveData.value = Event(true)
    }

    sealed class LoginResult {
        object LoginSuccess : LoginResult()
        class LoginError(val errorMsg: String?) : LoginResult()
    }
}