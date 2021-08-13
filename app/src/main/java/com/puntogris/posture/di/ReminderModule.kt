package com.puntogris.posture.di

import android.content.Context
import androidx.room.Room
import com.puntogris.posture.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ReminderModule {

    @Provides
    fun providesDayHistoryDao(appDatabase: AppDatabase) = appDatabase.dayHistoryDao()

    @Provides
    @Singleton
    fun provideReminderDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "reminder_table"
            )
                //.createFromAsset("databases/reminder_config.db")
            .build()
    }
}