package com.sygic.travel.sdk.session.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal data class AuthenticationRequest(
	val client_id: String? = null,
	val client_secret: String? = null,
	val token: String? = null,
	val grant_type: String? = null,
	val access_token: String? = null,
	val id_token: String? = null,
	val authorization_code: String? = null,
	val username: String? = null,
	val password: String? = null,
	val device_code: String? = null,
	val device_platform: String? = null,
	val refresh_token: String? = null
)
