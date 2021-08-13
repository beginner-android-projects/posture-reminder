package com.puntogris.posture.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.puntogris.posture.data.OnSaveListener
import com.puntogris.posture.di.realmApp
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.Report
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.model.User
import com.puntogris.posture.utils.Constants.BUG_REPORT_COLLECTION_NAME
import io.realm.Realm
import io.realm.kotlin.toFlow
import io.realm.kotlin.toflow
import io.realm.mongodb.AppException
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(
    private val realmDataSource: RealmDataSource): IRepository {

//    private val firestore = Firebase.firestore


    override fun login(
        userName: String,
        password: String,
        loginSuccess: (io.realm.mongodb.User) -> Unit,
        loginError: (AppException?) -> Unit
    ) =
        realmDataSource.login(userName, password, loginSuccess, loginError)

    override fun logOut(logoutSuccess: () -> Unit, logoutError: (Throwable?) -> Unit) {
        realmDataSource.logOut(logoutSuccess, logoutError)
    }

    override fun restoreLoggedUser() = realmDataSource.restoreLoggedUser()

    override fun getAllUserRemindersFlow(): Flow<List<Reminder>> {
        return realmDataSource.getAllUserReminders()
    }

    override fun deleteReminder(reminder: Reminder) {
        realmDataSource.deleteReminderWithTransaction(reminder)
    }

    override suspend fun sendReportToFirestore(report: Report): RepoResult {
        return try {
           // firestore.collection(BUG_REPORT_COLLECTION_NAME).document().set(report).await()
            RepoResult.Success
        }catch (e:Exception){
            RepoResult.Failure
        }
    }

    override fun getUserFlow(): Flow<User?> {
        return realmDataSource.getCurrentUserData()
    }

    override fun insertReminder(reminder: Reminder, saveListener: OnSaveListener) {
        realmDataSource.saveReminderWithTransaction(reminder, saveListener)
    }

}
