package com.sygic.travel.sdk.session.api.model

import com.google.gson.annotations.SerializedName

internal data class SessionResponse(
	@SerializedName("access_token")
	val accessToken: String,

	@SerializedName("expires_in")
	val expiresIn: Long,

	@SerializedName("refresh_token")
	val refreshToken: String
)
