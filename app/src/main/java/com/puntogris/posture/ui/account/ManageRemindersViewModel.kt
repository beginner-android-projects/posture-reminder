package com.puntogris.posture.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.puntogris.posture.data.OnSaveListener
import com.puntogris.posture.data.local.ReminderDao
import com.puntogris.posture.data.remote.Repository
import com.puntogris.posture.model.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageRemindersViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun getAllReminders() = repository.getAllUserRemindersFlow().asLiveData()

    fun deleteReminder(reminder: Reminder){
        repository.deleteReminder(reminder)
    }

    fun insertReminder(reminder: Reminder) {
        val copy = Reminder(
            _id = reminder._id,
            name = reminder.name,
            userId = reminder.userId,
            timeInterval = reminder.timeInterval,
            startTime = reminder.startTime,
            endTime = reminder.endTime,
            alarmDays = reminder.alarmDays,
            color = reminder.color,
            vibrationPattern = reminder.vibrationPattern,
            sound = reminder.sound
        )
        repository.insertReminder(copy)

    }

}