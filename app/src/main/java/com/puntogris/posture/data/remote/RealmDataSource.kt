package com.puntogris.posture.data.remote

import android.content.Context
import com.puntogris.posture.data.OnSaveListener
import com.puntogris.posture.di.realmApp
import com.puntogris.posture.model.Reminder
import com.puntogris.posture.model.Sound
import com.puntogris.posture.model.User
import com.puntogris.posture.utils.userId
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.delete
import io.realm.kotlin.toFlow
import io.realm.kotlin.where
import io.realm.mongodb.AppException
import io.realm.mongodb.Credentials
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.bson.Document
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealmDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    //realmApp is initialized in the Application
    private lateinit var realm: Realm

    fun login(
        userName: String,
        password: String,
        loginSuccess: (io.realm.mongodb.User) -> Unit,
        loginError: (AppException?) -> Unit
    ) {

        val appCredentials = Credentials.emailPassword(userName, password)
        realmApp.loginAsync(appCredentials) {
            if (it.error != null) {
                loginError.invoke(it.error)
            } else {
                val user = it.get()
                instantiateRealm(user)
                loginSuccess.invoke(user)
            }
        }
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

    fun saveReminderWithTransaction(
        reminder: Reminder,
        listener: OnSaveListener
    ) {
        realm.executeTransactionAsync(
            {
                reminder.userId = realmApp.userId()
                it.insertOrUpdate(reminder)

            },
            { listener.onSuccess() },
            { listener.onError(it) }
        )
    }


    fun deleteReminderWithTransaction(reminder: Reminder){
        realm.executeTransactionAsync {
            it.where<Reminder>().equalTo("_id", reminder._id).findFirst()?.deleteFromRealm()
        }
    }
}