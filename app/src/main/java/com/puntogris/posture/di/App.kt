package com.puntogris.posture.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.jakewharton.threetenabp.AndroidThreeTen
import com.puntogris.posture.utils.Constants
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App :Application(){

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        createNotificationChannel()
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