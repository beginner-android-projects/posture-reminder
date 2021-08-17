package com.puntogris.posture.ui.reminders.new_edit

import androidx.lifecycle.*
import com.puntogris.posture.data.local.ReminderDao
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.ToneItem
import com.puntogris.posture.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class NewReminderViewModel @Inject constructor(
    private val reminderDao: ReminderDao
) : ViewModel() {

    private val _reminderConfig = MutableLiveData(Reminder())
    val reminder: LiveData<Reminder> = _reminderConfig

    suspend fun saveReminderRoom() {
        reminderDao.insert(_reminderConfig.value!!)
    }

    fun updateReminder(reminder: Reminder) {
        _reminderConfig.value = reminder
    }

    fun saveReminderName(text: String) {
        _reminderConfig.value?.name = text
    }

    fun saveStartTime(time: Long) {
        _reminderConfig.setField { startTime = time.millisToMinutes() }
    }

    fun saveEndTime(time: Long) {
        _reminderConfig.setField { endTime = time.millisToMinutes() }
    }

    fun saveTimeInterval(time: Int) {
        _reminderConfig.setField { timeInterval = time }
    }

    fun saveReminderDays(days: List<Int>) {
        _reminderConfig.setField { alarmDays = days }
    }

    fun saveReminderColor(resource: Int) {
        _reminderConfig.setField { color = resource }
    }

    fun saveReminderVibrationPattern(position: Int){
        _reminderConfig.setField { vibrationPattern = position }
    }

    fun saveReminderSoundPattern(toneItem: ToneItem?){
        toneItem?.let {
            _reminderConfig.setField {
                soundUri = it.uri
                soundName = it.title
            }
        }
    }
}