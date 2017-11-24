package com.sygic.travel.sdk.common.api.model

internal class ApiResponse<out T>(
	val status_code: Int,
	val server_timestamp: String,
	val data: T?
)
