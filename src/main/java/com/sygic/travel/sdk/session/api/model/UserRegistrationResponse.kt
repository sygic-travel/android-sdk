package com.sygic.travel.sdk.session.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class UserRegistrationResponse(
	val id: String
)
