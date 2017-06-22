package com.sygic.travel.sdk.api.interceptors

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

import com.sygic.travel.sdk.api.StApiConstants.H_NAME_API_KEY
import com.sygic.travel.sdk.api.StApiConstants.H_NAME_CONTENT_TYPE
import com.sygic.travel.sdk.api.StApiConstants.H_NAME_USER_AGENT
import com.sygic.travel.sdk.api.StApiConstants.H_VALUE_CONTENT_TYPE

/**
 * Implements [okhttp3.Interceptor], adds headers to a requests.
 */
internal class HeadersInterceptor : Interceptor {
    private var apiKey: String? = null
    private var userAgent: String? = null

    /**
     * Sets API key
     * @param apiKey API key to be sent as a header in every request.
     */
    fun setApiKey(apiKey: String) {
        this.apiKey = apiKey
    }

    /**
     * Sets UserAgent
     * @param userAgent UserAgent to by sent as a header in every request.
     */
    fun setUserAgent(userAgent: String) {
        this.userAgent = userAgent
    }

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
