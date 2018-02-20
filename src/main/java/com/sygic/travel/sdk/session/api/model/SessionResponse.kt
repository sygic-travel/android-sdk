package com.sygic.travel.sdk.session.api.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class SessionResponse(
	@Json(name = "access_token")
	val accessToken: String,
	@Json(name = "expires_in")
	val expiresIn: Long,
	@Json(name = "refresh_token")
	val refreshToken: String
)
