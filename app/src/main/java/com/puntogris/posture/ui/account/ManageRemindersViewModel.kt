package com.puntogris.posture.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.puntogris.posture.data.Repository
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.RepoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ManageRemindersViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun getAllReminders() = repository.getAllUserRemindersFlow().asLiveData()

    fun deleteReminder(reminder: Reminder){
        repository.deleteReminder(reminder)
    }

    fun insertReminder(reminder: Reminder): StateFlow<RepoResult> {
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
        return repository.insertReminder(copy)

    }

}