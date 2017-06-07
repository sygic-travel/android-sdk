package com.sygic.travel.sdk.contentProvider.api.interceptors

import com.sygic.travel.sdk.contentProvider.api.StApiConstants.API_VERSION
import com.sygic.travel.sdk.contentProvider.api.StApiConstants.VERSION_AND_LOCALE
import com.sygic.travel.sdk.contentProvider.api.SupportedLanguages
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *
 * Implements [okhttp3.Interceptor], adds API version and locale code.
 */
class LocaleInterceptor : Interceptor {
    /**
     *
     * Device's current locale code
     */
    private var locale: String? = null

    init {
        updateLocale()
    }

    /**
     *
     * Updates [LocaleInterceptor.locale] to device's current locale code
     */
    fun updateLocale() {
        locale = SupportedLanguages.actualLocale
    }

    /**
     *
     * Modifies the original request by adding an **API version** and a **locale code**.
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var url = original.url().toString()

        url = url.replace(VERSION_AND_LOCALE, API_VERSION + "/" + locale)

        val request = original.newBuilder()
                .url(url)
                .method(original.method(), original.body())
                .build()

        return chain.proceed(request)
    }
}
