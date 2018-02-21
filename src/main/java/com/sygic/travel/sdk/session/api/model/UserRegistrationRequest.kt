package com.sygic.travel.sdk.session.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class UserRegistrationRequest(
	val username: String,
	val password: String,
	val email: String,
	val name: String,
	val email_is_verified: Boolean
)
