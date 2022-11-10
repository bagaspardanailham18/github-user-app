package com.bagas.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bagas.githubuserapp.data.local.UserDao
import com.bagas.githubuserapp.data.local.UserEntity

//class FakeUserDao: UserDao {
//
//    private var userData = mutableListOf<List<UserEntity>>()
//
//    override suspend fun insertUsers(users: List<UserEntity>) {
//        userData.add(users)
//    }
//
//    override fun getAllUser(): LiveData<List<UserEntity>> {
//        val observableUsers = MutableList<List<UserEntity>>()
//        observableUsers.value = userData
//        return observableUsers
//    }
//
//    override suspend fun deleteAllUser() {
//        userData.clear()
//    }
//}