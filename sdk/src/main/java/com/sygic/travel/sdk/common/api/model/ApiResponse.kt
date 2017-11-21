package com.sygic.travel.sdk.common.api.model

import com.google.gson.annotations.SerializedName

internal class ApiResponse<out T> {
	@SerializedName("status_code")
	val statusCode: Int = 0

	@SerializedName("error")
	val error: Error? = null

	val data: T? = null

	inner class Error {
		val id: String? = null
		val args: List<String>? = null
	}
}
