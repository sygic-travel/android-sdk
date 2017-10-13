package com.sygic.travel.sdk.common.interceptors

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

/**
 * Implements [okhttp3.Interceptor], adds headers to a requests.
 */
internal class HeadersInterceptor(
	private val apiKey: String,
	private val userAgent: String
) : Interceptor {

	/**
	 * Modifies the original request by adding an **API key**.
	 */
	@Throws(IOException::class)
	override fun intercept(chain: Chain): Response {
		val original = chain.request()
		val request = original.newBuilder()
			.addHeader("Content-Type", "application/json")
			.addHeader("x-api-key", apiKey)
			.addHeader("User-Agent", userAgent)
			.method(original.method(), original.body())
			.build()
		return chain.proceed(request)
	}
}
