package com.sygic.travel.sdk.session.api.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class AuthenticationRequest(
	@Json(name = "client_id")
	val clientId: String? = null,
	@Json(name = "client_secret")
	val clientSecret: String? = null,
	val token: String? = null,
	@Json(name = "grant_type")
	val grantType: String? = null,
	@Json(name = "access_token")
	val accessToken: String? = null,
	@Json(name = "id_token")
	val idToken: String? = null,
	@Json(name = "authorization_code")
	val authorizationCode: String? = null,
	val username: String? = null,
	val password: String? = null,
	@Json(name = "device_code")
	val deviceCode: String? = null,
	@Json(name = "device_platform")
	val devicePlatform: String? = null,
	@Json(name = "refresh_token")
	val refreshToken: String? = null
)
