package com.sygic.travel.sdk.common.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class ApiResponse<out T>(
	val status_code: Int,
	val server_timestamp: String,
	val data: T?
)
