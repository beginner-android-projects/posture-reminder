package com.puntogris.posture.data.repo.reminder

import com.puntogris.posture.data.local.ReminderDao
import com.puntogris.posture.data.remote.FirebaseReminderDataSource
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.RepoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class ReminderRepository @Inject constructor(
    private val reminderFirestore: FirebaseReminderDataSource,
    private val reminderDao: ReminderDao
): IReminderRepository {

    override fun getAllRemindersFromRoomLiveData() = reminderDao.getAllRemindersLiveData()

    override suspend fun deleteReminder(reminder: Reminder) :RepoResult = withContext(Dispatchers.IO){
        try {
            reminderDao.delete(reminder)
            reminderFirestore.getReminderRefWithId(reminder.id).delete().await()
            RepoResult.Success
        }catch (e:Exception){
            RepoResult.Failure
        }
    }

    override suspend fun insertReminder(reminder: Reminder): RepoResult = withContext(Dispatchers.IO){
        try {
            reminderDao.insert(reminder)
            reminderFirestore.getUserRemindersRef().document(reminder.id).set(reminder).await()
            RepoResult.Success
        }catch (e:Exception){
            RepoResult.Failure
        }
    }

    override suspend fun updateCurrentReminder(reminderId: String) {

    }

}