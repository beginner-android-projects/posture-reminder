package com.puntogris.posture.ui.account

import androidx.lifecycle.*
import com.puntogris.posture.data.local.DayHistoryDao
import com.puntogris.posture.data.remote.Repository
import com.puntogris.posture.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.kotlin.isValid
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val dayHistoryDao: DayHistoryDao,
    private val repository: Repository
): ViewModel() {

    suspend fun getWeekData() = dayHistoryDao.getWeekEntries()
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