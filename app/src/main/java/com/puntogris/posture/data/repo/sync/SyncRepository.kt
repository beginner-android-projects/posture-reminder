package com.puntogris.posture.data.repo.sync

import com.puntogris.posture.data.local.ReminderDao
import com.puntogris.posture.data.local.UserDao
import com.puntogris.posture.data.remote.FirebaseSyncDataSource
import com.puntogris.posture.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncRepository @Inject constructor(
    private val reminderDao: ReminderDao,
    private val userDao: UserDao,
    private val firestoreSync: FirebaseSyncDataSource
) : ISyncRepository {

    override suspend fun syncFirestoreAccountWithRoom(userPrivateData: UserPrivateData): RepoResult =
        withContext(Dispatchers.IO) {
            try {
                val userState = checkIfUserIsNewAndCreateIfNot(userPrivateData)
                if (userState is UserAccount.Registered) {
                    syncUserReminders()
                }
                RepoResult.Success
            } catch (e: Exception) {
                RepoResult.Failure
            }
        }

    private suspend fun checkIfUserIsNewAndCreateIfNot(user: UserPrivateData): UserAccount {
        val userDocument = firestoreSync.getUserPrivateDataRef().get().await()
        return if (!userDocument.exists()) {
            insertUserIntoRoomAndFirestore(user)
            UserAccount.New
        } else {
            UserAccount.Registered
        }
    }

    private suspend fun insertUserIntoRoomAndFirestore(user: UserPrivateData) {
        val userPublicProfileRef = firestoreSync.getUserPublicProfileRef()
        val userPrivateDataRef = firestoreSync.getUserPrivateDataRef()

        userDao.insert(user)
        firestoreSync.runBatch().apply {
            set(userPrivateDataRef, user)
            set(userPublicProfileRef, getPublicProfileFromUserPrivateData(user))
        }
    }

    private fun getPublicProfileFromUserPrivateData(user: UserPrivateData): UserPublicProfile {
        return UserPublicProfile(
            name = user.name,
            country = user.country,
            id = user.id
        )
    }

    private suspend fun syncUserReminders() {
        val reminders =
            firestoreSync.getUserRemindersQuery().get().await().toObjects(Reminder::class.java)
        reminderDao.insertRemindersIfNotInRoom(reminders)
    }

}