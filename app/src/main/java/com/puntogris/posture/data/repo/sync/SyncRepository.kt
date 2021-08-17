package com.puntogris.posture.data.repo.sync

import com.puntogris.posture.data.local.ReminderDao
import com.puntogris.posture.data.local.UserDao
import com.puntogris.posture.data.remote.FirebaseSyncDataSource
import com.puntogris.posture.model.UserPrivateData
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.model.UserPublicProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SyncRepository @Inject constructor(
    private val reminderDao: ReminderDao,
    private val userDao: UserDao,
    private val firestoreSync: FirebaseSyncDataSource
): ISyncRepository {

    override suspend fun syncFirestoreAccountWithRoom(userPrivateData: UserPrivateData): RepoResult {
        return try {
            val isUserNew = checkIfUserIsNewAndCreateOne(userPrivateData)
            if (!isUserNew){
                syncUserReminders()
            }
            RepoResult.Success
        }catch (e:Exception){ RepoResult.Failure }
    }

    private suspend fun checkIfUserIsNewAndCreateOne(userPrivateData: UserPrivateData): Boolean{
        val userPrivateDataRef = firestoreSync.getUserPrivateDataRef()
        val userPublicProfileRef = firestoreSync.getUserPublicProfileRef()

        val userDocument = userPrivateDataRef.get().await()

        return if (!userDocument.exists()) {
            val userPublicProfile = UserPublicProfile(
                name = userPrivateData.name,
                country = userPrivateData.country,
                userId = userPrivateData.userId
            )

            userDao.insert(userPrivateData)
            firestoreSync.getBatch().apply {
                set(userPrivateDataRef, userPrivateData)
                set(userPublicProfileRef, userPublicProfile)
            }
            false
        }else
            true
    }

    private suspend fun syncUserReminders(){
        val reminders = firestoreSync.getUserRemindersQuery().get().await().toObjects(Reminder::class.java)
        reminderDao.insertRemindersIfNotInRoom(reminders)
    }

}