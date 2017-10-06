package com.sygic.travel.sdk.common.responseWrappers

import com.google.gson.annotations.SerializedName

/**
 * Base of an API response. It's extended by specific respond classes containing the specific data.
 * It's abstract, because of the abstract [.getData] method.
 */
abstract class StResponse {
	@SerializedName("status_code")
	val statusCode: Int = 0

	@SerializedName("error")
	val error: Error? = null

	inner class Error {
		val id: String? = null
		val args: List<String>? = null
	}

	companion object {
		val STATUS_OK = 200
	}
}
