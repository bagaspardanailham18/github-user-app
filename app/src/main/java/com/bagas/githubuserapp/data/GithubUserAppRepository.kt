package com.bagas.githubuserapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bagas.githubuserapp.data.remote.ApiService
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.data.remote.responses.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserAppRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllUsers(): LiveData<Result<List<ListUserResponseItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllUsers()
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
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