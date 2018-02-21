package com.sygic.travel.sdk.session.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class SessionResponse(
	val access_token: String,
	val expires_in: Long,
	val refresh_token: String
)
