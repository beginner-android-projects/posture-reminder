package com.puntogris.posture.ui.main

import androidx.lifecycle.*
import com.puntogris.posture.Alarm
import com.puntogris.posture.BuildConfig
import com.puntogris.posture.data.Repository
import com.puntogris.posture.model.AuthState
import com.puntogris.posture.model.Report
import com.puntogris.posture.utils.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val alarm: Alarm,
    private val dataStore: DataStore
    ): ViewModel() {

    fun getAuthState(): AuthState {
        return if (repository.restoreLoggedUser() == null) AuthState.AuthRequired
        else AuthState.AuthComplete
    }

    private val _appVersionStatus = MutableLiveData<Boolean>()
    val appVersionStatus: LiveData<Boolean>
        get() = _appVersionStatus

    init {
        viewModelScope.launch {
            if (dataStore.lastVersionCode() < BuildConfig.VERSION_CODE) {
                dataStore.updateLastVersionCode()
                _appVersionStatus.value = true
            }
        }
    }

  //  private val _reminder = reminderDao.getReminderConfigLiveData()
   // val reminder: LiveData<Reminder> = _reminder


  //  fun isAppActive() = reminder.value!!.isActive

    fun startAlarm(){
        viewModelScope.launch {
        //    reminder.value?.let {
         //       alarm.startDailyAlarm(it)
       //         reminderDao.updateReminderStatus(it.isActive)
       //     }
        }
    }

    fun cancelAlarms(){
        viewModelScope.launch {
            alarm.cancelAlarms()
        //    reminderDao.updateReminderStatus(!reminder.value!!.isActive)
        }
    }

    fun refreshAlarms(){
        alarm.cancelAlarms()
      //  alarm.startDailyAlarm(reminder.value!!)
    }

    fun enablePandaAnimation(){
        viewModelScope.launch {
         //   reminderDao.enablePandaAnimation()
        }
    }

    fun closeRealmInstance(){
        repository.closeRealmInstance()
    }

    fun instantiateRealmWithCurrentUser(){
        repository.instantiateRealm()
    }

    //fun getLastTwoDaysHistory() = dayHistoryDao.getLastTwoEntries()

    suspend fun sendReport(report:Report) = repository.sendReportToFirestore(report)

}