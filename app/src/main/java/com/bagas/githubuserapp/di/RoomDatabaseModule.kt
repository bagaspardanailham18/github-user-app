package com.bagas.githubuserapp.di

import android.content.Context
import com.bagas.githubuserapp.data.local.GithubUserRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): GithubUserRoomDatabase = GithubUserRoomDatabase.getDatabase(context)

}