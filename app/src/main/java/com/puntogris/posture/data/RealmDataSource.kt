package com.puntogris.posture.data

import com.puntogris.posture.ui.main.realmApp
import com.puntogris.posture.model.*
import com.puntogris.posture.utils.userId
import io.realm.Realm
import io.realm.kotlin.toFlow
import io.realm.kotlin.where
import io.realm.mongodb.Credentials
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealmDataSource @Inject constructor() {

    private lateinit var userRealm: Realm
    private lateinit var sharedRealm: Realm

    fun loginWithEmailCredentials(
        userName: String,
        password: String
    ): StateFlow<LoginResult> {
        val result = MutableStateFlow<LoginResult>(LoginResult.InProgress)
        val appCredentials = Credentials.emailPassword(userName, password)
        realmApp.loginAsync(appCredentials) {
            if (it.error != null) {
                result.value = LoginResult.Error(it.error.localizedMessage)
            } else {
                val user = it.get()
                instantiateRealm(user)
                result.value = LoginResult.Success
            }
        }
        return result
    }

    fun logOut(
        logoutSuccess: () -> Unit,
        logoutError: (Throwable?) -> Unit
    ) {
        realmApp.currentUser()?.logOutAsync {
            if (it.error != null) logoutError.invoke(it.error.exception)
            else logoutSuccess.invoke()
        }
    }

    fun restoreLoggedUser(): io.realm.mongodb.User? {
        return realmApp.currentUser()?.also {
            instantiateRealm(it)
        }
    }

    private fun instantiateRealm(user: io.realm.mongodb.User) {
        val userConfiguration = SyncConfiguration
            .Builder(user, realmApp.userId())
            .build()

        val sharedConfiguration = SyncConfiguration
            .Builder(user, "shared")
            .build()

        userRealm = Realm.getInstance(userConfiguration)
        sharedRealm = Realm.getInstance(sharedConfiguration)
    }

    fun getCurrentUserData(): Flow<User?> {
        return userRealm.where<User>()
            .findFirstAsync()
            .toFlow()
    }

    fun getAllUserReminders(): Flow<List<Reminder>>{
        return userRealm.where<Reminder>()
            .findAll()
            .toFlow()
    }

    fun insertReminderWithTransaction(reminder: Reminder): StateFlow<RepoResult> {
        return MutableStateFlow<RepoResult>(RepoResult.InProgress).also { result ->
            userRealm.executeTransactionAsync(
                {
                    reminder._partition = realmApp.userId()
                    it.insertOrUpdate(reminder)
                },
                { result.value = RepoResult.Success },
                { result.value = RepoResult.Error }
            )
        }
    }

    fun deleteReminderWithTransaction(reminder: Reminder){
        userRealm.executeTransactionAsync {
            it.where<Reminder>().equalTo("_id", reminder._id).findFirst()?.deleteFromRealm()
        }
    }

    fun instantiateRealmWithCurrentUser(){
        realmApp.currentUser()?.let {
            instantiateRealm(it)
        }
    }

    fun getLastTwoDaysHistory(): Flow<List<DayLog>>{
        return userRealm.where<DayLog>()
            .greaterThan("date", Date())
            .limit(2)
            .findAllAsync()
            .toFlow()
    }

    fun getTopThreeRankings(): Flow<List<User>>{
        return sharedRealm.where<User>()
            .limit(3)
            .findAll()
            .toFlow()
    }

    fun closeRealm(){
        userRealm.close()
        sharedRealm.close()
    }
}