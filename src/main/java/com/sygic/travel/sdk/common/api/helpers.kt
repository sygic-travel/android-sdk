package com.sygic.travel.sdk.common.api

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response

internal fun rangeFormatter(min: Int?, max: Int?): String? {
	return when {
		min != null && max != null -> "$min:$max"
		min != null -> "$min:"
		max != null -> ":$max"
		else -> null
	}
}

internal fun <T> Call<T>.checkedExecute(): Response<T> {
	val response = this.execute()
	if (!response.isSuccessful) {
		throw HttpException(response)
	}
	return response
}
