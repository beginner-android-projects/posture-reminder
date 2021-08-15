package com.puntogris.posture.ui.account

import androidx.lifecycle.ViewModel
import com.puntogris.posture.data.local.ReminderDao
import com.puntogris.posture.data.local.UserDao
import com.puntogris.posture.model.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageRemindersViewModel @Inject constructor(
    private val reminderDao: ReminderDao,
    private val userDao: UserDao
): ViewModel() {

    fun getAllReminders() = reminderDao.getAllRemindersFlow()

    suspend fun deleteReminder(reminder: Reminder) = reminderDao.delete(reminder)

    suspend fun insertReminder(reminder: Reminder) = reminderDao.insert(reminder)

    suspend fun updateCurrentReminder(reminderId: Int) = userDao.updateCurrentUserReminder(reminderId)
}