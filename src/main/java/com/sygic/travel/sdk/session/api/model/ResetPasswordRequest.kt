package com.sygic.travel.sdk.session.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ResetPasswordRequest(
	val email: String
)
