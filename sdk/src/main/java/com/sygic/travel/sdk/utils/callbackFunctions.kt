package com.sygic.travel.sdk.utils

import com.sygic.travel.sdk.Callback
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

fun <T> runWithCallback(f: suspend () -> T, callback: Callback<T>?) {
	launch(CommonPool) {
		try {
			val data = f()
			callback?.onSuccess(data)
		} catch (e: Exception) {
			callback?.onFailure(e)
		}
	}
}

suspend fun <T> runAsync(f: suspend () -> T): T {
	return async(CommonPool) {
		f()
	}.await()
}
