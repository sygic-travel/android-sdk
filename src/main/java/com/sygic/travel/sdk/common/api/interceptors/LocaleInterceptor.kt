package com.sygic.travel.sdk.common.api.interceptors

import com.sygic.travel.sdk.common.SupportedLanguages
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

internal class LocaleInterceptor : Interceptor {
	private var locale: String? = null

	init {
		updateLocale()
	}

	companion object {
		const val LOCALE_PLACEHOLDER = "[api_locale]"
	}

	fun updateLocale() {
		locale = SupportedLanguages.actualLocale
	}

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
