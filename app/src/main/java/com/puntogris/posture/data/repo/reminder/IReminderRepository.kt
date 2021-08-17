package com.puntogris.posture.data.repo.reminder

import androidx.lifecycle.LiveData
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.RepoResult

interface IReminderRepository {
    fun getAllRemindersFromRoomLiveData(): LiveData<List<Reminder>>
    suspend fun deleteReminder(reminder: Reminder): RepoResult
    suspend fun insertReminder(reminder: Reminder): RepoResult
    suspend fun updateCurrentReminder(reminderId: String)
}