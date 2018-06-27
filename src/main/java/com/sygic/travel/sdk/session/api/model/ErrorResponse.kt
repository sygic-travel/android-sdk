package com.sygic.travel.sdk.session.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ErrorResponse(
	val type: String
)
