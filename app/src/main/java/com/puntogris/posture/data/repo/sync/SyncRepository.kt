package com.puntogris.posture.data.repo.sync

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.puntogris.posture.data.local.ReminderDao
import com.puntogris.posture.data.local.UserDao
import com.puntogris.posture.model.UserPrivateData
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.model.UserPublicProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SyncRepository @Inject constructor(
    private val reminderDao: ReminderDao,
    private val userDao: UserDao
): ISyncRepository {

    private val firestore = Firebase.firestore

    override suspend fun syncFirestoreAccountWithRoom(userPrivateData: UserPrivateData): RepoResult {
        return try {
            val isUserNew = checkIfUserIsNewAndCreateOne(userPrivateData)
            if (!isUserNew){
                syncUserReminders(userPrivateData)
            }
            RepoResult.Success
        }catch (e:Exception){ RepoResult.Failure }
    }

    private suspend fun checkIfUserIsNewAndCreateOne(userPrivateData: UserPrivateData): Boolean{
        val userPrivateDataRef = firestore
            .collection("users")
            .document(userPrivateData.userId)

        val userPublicProfileRef = firestore
            .collection("users")
            .document(userPrivateData.userId)
            .collection("publicProfile")
            .document("profile")

        val userDocument = userPrivateDataRef.get().await()

        return if (!userDocument.exists()) {
            val userPublicProfile = UserPublicProfile(
                name = userPrivateData.name,
                country = userPrivateData.country,
                userId = userPrivateData.userId
            )
            firestore.runBatch {
                it.set(userPrivateDataRef, userPrivateData)
                it.set(userPublicProfileRef, userPublicProfile)
            }
            false
        }else true
    }

    private suspend fun syncUserReminders(userPrivateData: UserPrivateData){
        val reminders = firestore
            .collection("users")
            .document(userPrivateData.userId)
            .collection("reminders")
            .limit(10)
            .get()
            .await()
            .toObjects(Reminder::class.java)

        reminderDao.insertRemindersIfNotInRoom(reminders)
    }

}