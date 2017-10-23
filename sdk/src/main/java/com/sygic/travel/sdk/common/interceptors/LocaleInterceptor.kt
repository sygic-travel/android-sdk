package com.sygic.travel.sdk.common.interceptors

import com.sygic.travel.sdk.common.SupportedLanguages
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

/**
 * Implements [okhttp3.Interceptor], adds API version and locale code.
 */
internal class LocaleInterceptor : Interceptor {
	/**
	 * Device's current locale code
	 */
	private var locale: String? = null

	init {
		updateLocale()
	}

	companion object {
		const val LOCALE_PLACEHOLDER = "[api_locale]"
	}

	/**
	 * Updates [LocaleInterceptor.locale] to device's current locale code
	 */
	fun updateLocale() {
		locale = SupportedLanguages.actualLocale
	}

	/**
	 * Modifies the original request by adding **locale code**.
	 */
	@Throws(IOException::class)
	override fun intercept(chain: Chain): Response {
		val original = chain.request()
		var url = original.url().toString()

		url = url.replace(LOCALE_PLACEHOLDER, locale.toString())

		val request = original.newBuilder()
			.url(url)
			.method(original.method(), original.body())
			.build()

		return chain.proceed(request)
	}
}
