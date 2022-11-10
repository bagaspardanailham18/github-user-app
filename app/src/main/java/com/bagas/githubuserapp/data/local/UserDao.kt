package com.bagas.githubuserapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM user_entities")
    suspend fun deleteAllUser()

    @Query("SELECT * FROM user_entities")
    fun getAllUser(): LiveData<List<UserEntity>>

}