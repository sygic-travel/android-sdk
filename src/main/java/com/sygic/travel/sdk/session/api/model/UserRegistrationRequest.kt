package com.sygic.travel.sdk.session.api.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class UserRegistrationRequest(
	val username: String,
	val password: String,
	val email: String,
	val name: String,
	@Json(name = "email_is_verified")
	val emailIsVerified: Boolean
)
