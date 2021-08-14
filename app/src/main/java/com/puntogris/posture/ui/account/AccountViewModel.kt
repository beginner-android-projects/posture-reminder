package com.puntogris.posture.ui.account

import androidx.lifecycle.*
import com.puntogris.posture.data.Repository
import com.puntogris.posture.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

   // suspend fun getWeekData() = dayHistoryDao.getWeekEntries()
    private val _currentUser = MutableLiveData(User())
    val currentUser: LiveData<User> = _currentUser


    init {
        viewModelScope.launch {
            repository.getUserFlow().collect {
                if (it != null && it.isValid) _currentUser.value = it
            }
        }
    }


}