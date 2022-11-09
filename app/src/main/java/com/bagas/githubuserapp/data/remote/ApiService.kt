package com.bagas.githubuserapp.data.remote

import com.bagas.githubuserapp.BuildConfig
import com.bagas.githubuserapp.data.remote.responses.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: Bearer ${BuildConfig.GITHUB_API_TOKEN}")
    @GET("users")
    suspend fun getAllUsers() : List<ListUserResponseItem>

    @Headers("Authorization: Bearer ${BuildConfig.GITHUB_API_TOKEN}")
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ) : DetailUserResponse

    @GET("search/users")
    @Headers("Authorization: Bearer ${BuildConfig.GITHUB_API_TOKEN}")
    suspend fun getSearchedUsers(
        @Query("q") q: String
    ) : SearchUserResponse

    @GET("users/{username}/repos")
    @Headers("Authorization: Bearer ${BuildConfig.GITHUB_API_TOKEN}")
    suspend fun getReposForAUser(
        @Path("username") username: String
    ) : List<ListRepositoriesForAUserResponseItem>

}