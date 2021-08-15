package com.puntogris.posture.data

import com.puntogris.posture.model.*
import io.realm.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IRepository {
    suspend fun sendReportToFirestore(ticket: Ticket): RepoResult
    fun getUserFlow(): Flow<User?>
    fun insertReminder(reminder: Reminder): StateFlow<RepoResult>
    fun loginUserWithEmail(userName: String, password: String): StateFlow<LoginResult>
    fun logOut(logoutSuccess: () -> Unit, logoutError: (Throwable?) -> Unit)
    fun restoreLoggedUser():io.realm.mongodb.User?
    fun getAllUserRemindersFlow(): Flow<List<Reminder>>
    fun deleteReminder(reminder: Reminder)
    fun instantiateRealm()
    fun getLastTwoEntries(): Flow<List<DayLog>>
    fun getTopThreeExpRankings(): RealmResults<RankingProfile>
    fun getGlobalRankings(): RealmResults<RankingProfile>
}