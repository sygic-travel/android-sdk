package com.sygic.travel.sdk.session.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SessionResponse(
	val access_token: String,
	val expires_in: Long,
	val refresh_token: String
)
