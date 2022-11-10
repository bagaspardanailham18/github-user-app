package com.bagas.githubuserapp.data.remote.responses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ListUserResponse(

	@field:SerializedName("ListUserResponse")
	val listUserResponse: List<ListUserResponseItem?>? = null
)

data class ListUserResponseItem(
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null
)
