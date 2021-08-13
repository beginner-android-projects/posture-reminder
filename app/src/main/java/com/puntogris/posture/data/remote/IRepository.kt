package com.puntogris.posture.data.remote

import com.puntogris.posture.data.OnSaveListener
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.Report
import com.puntogris.posture.model.RepoResult
import com.puntogris.posture.model.User
import io.realm.mongodb.AppException
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun sendReportToFirestore(report: Report): RepoResult
    fun getUserFlow(): Flow<User?>
    fun insertReminder(reminder: Reminder, saveListener: OnSaveListener)
    fun login(
        userName: String,
        password: String,
        loginSuccess: (io.realm.mongodb.User) -> Unit,
        loginError: (AppException?) -> Unit
    )

    fun logOut(logoutSuccess: () -> Unit, logoutError: (Throwable?) -> Unit)
    fun restoreLoggedUser():io.realm.mongodb.User?
    fun getAllUserRemindersFlow(): Flow<List<Reminder>>
    fun deleteReminder(reminder: Reminder)
}