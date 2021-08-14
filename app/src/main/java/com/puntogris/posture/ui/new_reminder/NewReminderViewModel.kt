package com.puntogris.posture.ui.new_reminder

import androidx.lifecycle.*
import com.puntogris.posture.data.Repository
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.Sound
import com.puntogris.posture.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.RealmList

import javax.inject.Inject

@HiltViewModel
class NewReminderViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _reminderConfig = MutableLiveData(Reminder())
    val reminder: LiveData<Reminder> = _reminderConfig

    fun saveReminder() = repository.insertReminder(_reminderConfig.value!!)


    fun updateReminder(reminder: Reminder) {
        val copy = Reminder(
            _id = reminder._id,
            name = reminder.name,
            _partition = reminder._partition,
            timeInterval = reminder.timeInterval,
            startTime = reminder.startTime,
            endTime = reminder.endTime,
            alarmDays = reminder.alarmDays,
            color = reminder.color,
            vibrationPattern = reminder.vibrationPattern,
            sound = reminder.sound
        )
        _reminderConfig.value = copy
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
        val realmList = RealmList<Int>()
        realmList.addAll(days)
        _reminderConfig.setField { alarmDays = realmList }
    }

    fun saveReminderColor(resource: Int) {
        _reminderConfig.setField { color = resource }
    }

    fun saveReminderVibrationPattern(position: Int){
        _reminderConfig.setField { vibrationPattern = position }
    }

    fun saveReminderSoundPattern(sound: Sound){
        _reminderConfig.setField {
            this.sound = sound
        }
    }
}