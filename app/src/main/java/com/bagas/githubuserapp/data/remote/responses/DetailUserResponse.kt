package com.bagas.githubuserapp.data.remote.responses

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)
