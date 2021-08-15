package com.puntogris.posture.ui.login

import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.Repository
import com.puntogris.posture.data.remote.Repository
import com.puntogris.posture.model.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    fun startLoginWithEmail(userName: String, password: String): StateFlow<LoginResult> =
        repository.loginUserWithEmail(userName = userName, password = password)

}