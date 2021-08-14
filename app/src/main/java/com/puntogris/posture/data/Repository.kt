package com.puntogris.posture.data

import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.Report
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.model.User
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(
    private val realmDataSource: RealmDataSource
): IRepository {

    override fun loginUserWithEmail(userName: String, password: String) =
        realmDataSource.loginWithEmailCredentials(userName, password)

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

    override fun instantiateRealm() {
        realmDataSource.instantiateRealmWithCurrentUser()
    }

    override suspend fun sendReportToFirestore(report: Report): RepoResult {
        return try {
           // firestore.collection(BUG_REPORT_COLLECTION_NAME).document().set(report).await()
            RepoResult.Success
        }catch (e:Exception){
            RepoResult.Error
        }
    }

    override fun getUserFlow(): Flow<User?> {
        return realmDataSource.getCurrentUserData()
    }

    override fun insertReminder(reminder: Reminder) =
        realmDataSource.insertReminderWithTransaction(reminder)


    fun closeRealmInstance(){
        realmDataSource.closeRealm()
    }

}
