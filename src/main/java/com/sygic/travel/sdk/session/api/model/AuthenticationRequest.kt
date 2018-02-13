package com.sygic.travel.sdk.session.api.model

import com.google.gson.annotations.SerializedName

internal data class AuthenticationRequest(
	@SerializedName("client_id")
	val clientId: String? = null,

	@SerializedName("client_secret")
	val clientSecret: String? = null,

	@SerializedName("token")
	val token: String? = null,

	@SerializedName("grant_type")
	val grantType: String? = null,

	@SerializedName("access_token")
	val accessToken: String? = null,

	@SerializedName("id_token")
	val idToken: String? = null,

	@SerializedName("authorization_code")
	val authorizationCode: String? = null,

	@SerializedName("username")
	val username: String? = null,

	@SerializedName("password")
	val password: String? = null,

	@SerializedName("device_code")
	val deviceCode: String? = null,

	@SerializedName("device_platform")
	val devicePlatform: String? = null,

	@SerializedName("refresh_token")
	val refreshToken: String? = null
)
