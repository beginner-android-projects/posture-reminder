package com.puntogris.posture.data.remote

import com.puntogris.posture.data.OnSaveListener
import com.puntogris.posture.model.*
import io.realm.mongodb.AppException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IRepository {
    suspend fun sendReportToFirestore(report: Report): RepoResult
    fun getUserFlow(): Flow<User?>
    fun insertReminder(reminder: Reminder): StateFlow<RepoResult>
    fun loginUserWithEmail(userName: String, password: String): StateFlow<LoginResult>
    fun logOut(logoutSuccess: () -> Unit, logoutError: (Throwable?) -> Unit)
    fun restoreLoggedUser():io.realm.mongodb.User?
    fun getAllUserRemindersFlow(): Flow<List<Reminder>>
    fun deleteReminder(reminder: Reminder)
    fun instantiateRealm()
}