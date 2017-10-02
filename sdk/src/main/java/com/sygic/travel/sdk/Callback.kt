package com.sygic.travel.sdk

interface Callback<in T> {
	fun onSuccess(data: T)
	fun onFailure(exception: Throwable)
}
