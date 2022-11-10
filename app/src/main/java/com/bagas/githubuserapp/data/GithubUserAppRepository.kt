package com.bagas.githubuserapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bagas.githubuserapp.data.local.GithubUserRoomDatabase
import com.bagas.githubuserapp.data.local.UserEntity
import com.bagas.githubuserapp.data.remote.ApiService
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.data.remote.responses.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserAppRepository @Inject constructor(
    private val apiService: ApiService,
    private val githubUserRoomDatabase: GithubUserRoomDatabase
    ) {

    fun getAllUsers(): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllUsers()
            val userList = response.map { data ->
                UserEntity(
                    data.id,
                    data.login,
                    data.avatarUrl
                )
            }
            githubUserRoomDatabase.userDao().deleteAllUser()
            githubUserRoomDatabase.userDao().insertUsers(userList)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<UserEntity>>> = githubUserRoomDatabase.userDao().getAllUser().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getSearchedUsers(query: String): LiveData<Result<SearchUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSearchedUsers(query)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getReposForAUser(username: String): LiveData<Result<List<ListRepositoriesForAUserResponseItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getReposForAUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

}