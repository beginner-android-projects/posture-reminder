package com.puntogris.posture.data

import android.content.Context
import com.puntogris.posture.ui.main.realmApp
import com.puntogris.posture.model.*
import com.puntogris.posture.utils.userId
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.Realm
import io.realm.kotlin.toFlow
import io.realm.kotlin.where
import io.realm.mongodb.Credentials
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealmDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    //realmApp is initialized in the Application
    private lateinit var realm: Realm

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
        val configuration = SyncConfiguration
            .Builder(user, realmApp.userId())
            .build()

        realm = Realm.getInstance(configuration)
    }

    fun getCurrentUserData(): Flow<User?> {
        return realm.where<User>()
            .equalTo("userId", realmApp.userId())
            .findFirstAsync()
            .toFlow()
    }

    fun getAllUserReminders(): Flow<List<Reminder>>{
        return realm.where<Reminder>()
            .equalTo("userId", realmApp.userId())
            .findAll()
            .toFlow()
    }

    fun insertReminderWithTransaction(reminder: Reminder): StateFlow<RepoResult> {
        return MutableStateFlow<RepoResult>(RepoResult.InProgress).also { result ->
            realm.executeTransactionAsync(
                {
                    reminder.userId = realmApp.userId()
                    it.insertOrUpdate(reminder)
                },
                { result.value = RepoResult.Success },
                { result.value = RepoResult.Error }
            )
        }
    }


    fun deleteReminderWithTransaction(reminder: Reminder){
        realm.executeTransactionAsync {
            it.where<Reminder>().equalTo("_id", reminder._id).findFirst()?.deleteFromRealm()
        }
    }


    fun instantiateRealmWithCurrentUser(){
        realmApp.currentUser()?.let {
            instantiateRealm(it)
        }
    }


    fun closeRealm(){
        realm.close()
    }
}