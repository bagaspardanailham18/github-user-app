package com.bagas.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bagas.githubuserapp.data.remote.responses.ListUserResponseItem

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class GithubUserRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubUserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubUserRoomDatabase::class.java, "github_user_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}