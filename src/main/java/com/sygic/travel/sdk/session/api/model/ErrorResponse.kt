package com.sygic.travel.sdk.session.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ErrorResponse(
	val type: String
)
