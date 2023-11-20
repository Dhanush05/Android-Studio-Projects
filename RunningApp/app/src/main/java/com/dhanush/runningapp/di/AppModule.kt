package com.dhanush.runningapp.di

import android.content.Context
import androidx.room.Room
import com.dhanush.runningapp.db.RunningDatabase
import com.dhanush.runningapp.di.constants.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton // This annotation is used to tell dagger that this method will return a singleton object
    @Provides
    fun provideRunningDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,RunningDatabase::class.java, RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: RunningDatabase) = db.getRunDao() // This method will return the RunDao object
}