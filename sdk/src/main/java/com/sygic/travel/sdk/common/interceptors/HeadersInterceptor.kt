package com.sygic.travel.sdk.common.interceptors

import com.sygic.travel.sdk.common.StApiConstants.H_NAME_API_KEY
import com.sygic.travel.sdk.common.StApiConstants.H_NAME_CONTENT_TYPE
import com.sygic.travel.sdk.common.StApiConstants.H_NAME_USER_AGENT
import com.sygic.travel.sdk.common.StApiConstants.H_VALUE_CONTENT_TYPE

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
			.addHeader(H_NAME_CONTENT_TYPE, H_VALUE_CONTENT_TYPE)
			.addHeader(H_NAME_API_KEY, apiKey)
			.addHeader(H_NAME_USER_AGENT, userAgent)
			.method(original.method(), original.body())
			.build()
		return chain.proceed(request)
	}
}
