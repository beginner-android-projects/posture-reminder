package com.puntogris.posture.ui

import androidx.lifecycle.*
import com.puntogris.posture.Alarm
import com.puntogris.posture.data.local.DayHistoryDao
import com.puntogris.posture.data.local.ReminderDao
import com.puntogris.posture.data.remote.Repository
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val reminderDao: ReminderDao,
        private val repository: Repository,
        private val alarm: Alarm,
        private val dayHistoryDao: DayHistoryDao
    ): ViewModel() {

    private val _reminder = reminderDao.getReminderConfigLiveData()
    val reminder: LiveData<Reminder> = _reminder


    fun isAppActive() = reminder.value!!.isActive

    fun startAlarm(){
        viewModelScope.launch {
            reminder.value?.let {
                alarm.startDailyAlarm(it)
                reminderDao.updateReminderStatus(it.isActive)
            }
        }
    }

    fun cancelAlarms(){
        viewModelScope.launch {
            alarm.cancelAlarms()
            reminderDao.updateReminderStatus(!reminder.value!!.isActive)
        }
    }

    fun refreshAlarms(){
        alarm.cancelAlarms()
        alarm.startDailyAlarm(reminder.value!!)
    }

    fun enablePandaAnimation(){
        viewModelScope.launch {
         //   reminderDao.enablePandaAnimation()
        }
    }

    fun getLastTwoDaysHistory() = dayHistoryDao.getLastTwoEntries()

    suspend fun sendReport(report:Report) = repository.sendReportToFirestore(report)
}