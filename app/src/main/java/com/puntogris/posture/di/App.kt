package com.puntogris.posture.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.jakewharton.threetenabp.AndroidThreeTen
import com.puntogris.posture.BuildConfig
import com.puntogris.posture.data.remote.RealmDataSource
import com.puntogris.posture.utils.Constants
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import javax.inject.Inject

lateinit var realmApp: App

@HiltAndroidApp
class App: Application(){

    @Inject lateinit var realmDataSource: RealmDataSource
    var realm : Realm? = null
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        createNotificationChannel()

        Realm.init(this)

        realmApp = App(AppConfiguration.Builder(BuildConfig.REALM_APP_ID).defaultClientResetHandler { _, error ->
            Log.d("RealmApp Handler", error?.localizedMessage.toString())
        }.build())

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                Constants.POSTURE_NOTIFICATION_ID,
                Constants.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).also {
                it.description = Constants.CHANNEL_DESCRIPTION
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(it)
            }
        }
    }
}