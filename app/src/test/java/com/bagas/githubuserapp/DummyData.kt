package com.bagas.githubuserapp

import com.bagas.githubuserapp.data.local.UserEntity
import com.bagas.githubuserapp.data.remote.responses.*

object DummyData {
    fun generateDummyUserEntity(): List<UserEntity> {
        val userList = ArrayList<UserEntity>()
        for (i in 0..10) {
            val user = UserEntity(
                i,
                "login $i",
                "avatar $i"
            )
            userList.add(user)
        }
        return userList
    }

    fun generateDummyUsersResponse(): List<ListUserResponseItem> {
        val items = ArrayList<ListUserResponseItem>()
        for (i in 0..100) {
            val user = ListUserResponseItem(
                i,
                "login $i",
                "avatar $i"
            )
            items.add(user)
        }
        return items
    }

    fun generateDummySearchedUsersResponse(): SearchUserResponse {
        val items: MutableList<SearchedUserItem> = mutableListOf()
        for (i in 0..100) {
            val user = SearchedUserItem(
                "login $i",
                "avatarUrl $i",
                i
            )
            items.add(user)
        }
        return SearchUserResponse(100, false, items)
    }

    fun generateDummyUserDetailResponse(): DetailUserResponse {
       return DetailUserResponse(
           "bio",
           "login",
           1,
           "avatarUrl",
           "name"
       )
    }

    fun generateDummyUserReposResponse(): ListRepositoriesForAUserResponse {
        val items: MutableList<ListRepositoriesForAUserResponseItem> = mutableListOf()
        for (i in 0..100) {
            val repo = ListRepositoriesForAUserResponseItem(
                10,
                1,
                "fullName",
                "name",
                "description",
                "createdAt",
                "updatedAt"
            )
            items.add(repo)
        }
        return ListRepositoriesForAUserResponse(items)
    }
}