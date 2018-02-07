package com.sygic.travel.sdk.session.model

import com.google.gson.annotations.SerializedName

data class UserSession(
	@SerializedName("access_token")
	val accessToken: String,

	@SerializedName("expires_in")
	val expiresIn: Long,

	@SerializedName("refresh_token")
	val refreshToken: String
)
