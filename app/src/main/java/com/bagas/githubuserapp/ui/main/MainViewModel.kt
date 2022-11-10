package com.bagas.githubuserapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bagas.githubuserapp.data.GithubUserAppRepository
import com.bagas.githubuserapp.data.local.UserEntity
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.data.remote.responses.ListUserResponseItem
import com.bagas.githubuserapp.data.remote.responses.SearchUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val githubUserAppRepository: GithubUserAppRepository): ViewModel() {

    fun getAllUsers(): LiveData<Result<List<UserEntity>>> =
        githubUserAppRepository.getAllUsers()

    fun getSearchedUsers(query: String): LiveData<Result<SearchUserResponse>> =
        githubUserAppRepository.getSearchedUsers(query)

}