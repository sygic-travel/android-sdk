package com.sygic.travel.sdk.common.api.interceptors

import com.sygic.travel.sdk.common.api.TIMEOUT_HEADER
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

internal class TimeoutInterceptor : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val original = chain.request()

		val timeout = original.headers()[TIMEOUT_HEADER]?.toIntOrNull()
		@Suppress("LiftReturnOrAssignment")
		if (timeout != null) {
			val request = original.newBuilder()
				.removeHeader(TIMEOUT_HEADER)
				.build()

			return chain
				.withReadTimeout(timeout, TimeUnit.SECONDS)
				.proceed(request)
		} else {
			return chain.proceed(original)
		}
	}
}
