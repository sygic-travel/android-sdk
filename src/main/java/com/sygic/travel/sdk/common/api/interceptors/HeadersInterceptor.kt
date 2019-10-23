package com.sygic.travel.sdk.common.api.interceptors

import com.sygic.travel.sdk.session.service.AuthStorageService
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

internal class HeadersInterceptor constructor(
	private val authStorageService: (() -> AuthStorageService)?,
	private val apiKey: String?,
	private val userAgent: String
) : Interceptor {
	@Throws(IOException::class)
	override fun intercept(chain: Chain): Response {
		val original = chain.request()
		val requestBuilder = original.newBuilder()
			.addHeader("Content-Type", "application/json")
			.addHeader("User-Agent", userAgent)
			.method(original.method, original.body)

		if (apiKey != null) {
			requestBuilder.addHeader("x-api-key", apiKey)
		}

		if (original.header("Authorization") == "[toIntercept]") {
			val accessToken = authStorageService!!().getUserSession()
				?: throw IllegalStateException("The request requires an active user session.")

			requestBuilder
				.removeHeader("Authorization")
				.addHeader("Authorization", "Bearer $accessToken")
		}

		val request = requestBuilder.build()
		return chain.proceed(request)
	}
}
