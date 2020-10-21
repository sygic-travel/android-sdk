package com.sygic.travel.sdk.common.api.interceptors

import com.sygic.travel.sdk.common.Language
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

internal class LocaleInterceptor constructor(
	defaultLanguageCode: String
) : Interceptor {
	companion object {
		const val LOCALE_PLACEHOLDER = "[api_locale]"
	}

	private var locale: String = defaultLanguageCode

	fun updateLanguage(language: Language) {
		locale = language.isoCode
	}

	@Throws(IOException::class)
	override fun intercept(chain: Chain): Response {
		val original = chain.request()
		var url = original.url.toString()

		url = url.replace(LOCALE_PLACEHOLDER, locale)

		val request = original.newBuilder()
			.url(url)
			.method(original.method, original.body)
			.build()

		return chain.proceed(request)
	}
}
