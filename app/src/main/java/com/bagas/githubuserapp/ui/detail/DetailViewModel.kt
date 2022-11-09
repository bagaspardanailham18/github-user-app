package com.bagas.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.data.GithubUserAppRepository
import com.bagas.githubuserapp.data.remote.responses.DetailUserResponse
import com.bagas.githubuserapp.data.remote.responses.ListRepositoriesForAUserResponseItem
import com.bagas.githubuserapp.data.remote.responses.ListUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val githubUserAppRepository: GithubUserAppRepository): ViewModel() {

    fun getDetailUser(username: String): LiveData<Result<DetailUserResponse>> =
        githubUserAppRepository.getDetailUser(username)

    fun getUserRepos(username: String): LiveData<Result<List<ListRepositoriesForAUserResponseItem>>> =
        githubUserAppRepository.getReposForAUser(username)

}