package com.bagas.githubuserapp.data

import com.bagas.githubuserapp.DummyData
import com.bagas.githubuserapp.data.remote.ApiService
import com.bagas.githubuserapp.data.remote.responses.*

class FakeApiService: ApiService {

    private val dummyUserResponse = DummyData.generateDummyUsersResponse()
    private val dummySearchedUserResponse = DummyData.generateDummySearchedUsersResponse()
    private val dummyUserDetailResponse = DummyData.generateDummyUserDetailResponse()
    private val dummyUserRepoResponse = DummyData.generateDummyUserReposResponse()

    override suspend fun getAllUsers(): List<ListUserResponseItem> {
        return dummyUserResponse
    }

    override suspend fun getDetailUser(username: String): DetailUserResponse {
        return dummyUserDetailResponse
    }

    override suspend fun getSearchedUsers(q: String): SearchUserResponse {
        return dummySearchedUserResponse
    }

    override suspend fun getReposForAUser(username: String): ListRepositoriesForAUserResponse {
        return dummyUserRepoResponse
    }

}