package com.sygic.travel.sdk.api.interceptors

import com.sygic.travel.sdk.api.StApiConstants.API_VERSION
import com.sygic.travel.sdk.api.StApiConstants.VERSION_AND_LOCALE
import com.sygic.travel.sdk.api.SupportedLanguages

/**
 *
 * Implements [okhttp3.Interceptor], adds API version and locale code.
 */
internal class LocaleInterceptor : okhttp3.Interceptor {
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
    @Throws(java.io.IOException::class)
    override fun intercept(chain: okhttp3.Interceptor.Chain): okhttp3.Response {
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
