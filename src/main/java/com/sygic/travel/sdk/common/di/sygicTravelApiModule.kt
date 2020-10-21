package com.sygic.travel.sdk.common.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.TIMEOUT_DEFAULT
import com.sygic.travel.sdk.common.api.interceptors.HeadersInterceptor
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor.Companion.LOCALE_PLACEHOLDER
import com.sygic.travel.sdk.common.api.interceptors.TimeoutInterceptor
import com.sygic.travel.sdk.session.service.AuthStorageService
import com.sygic.travel.sdk.utils.UserAgentUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal val sygicTravelApiModule = module {
	single {
		LocaleInterceptor(getProperty("defaultLanguage"))
	}
	single {
		TimeoutInterceptor()
	}
	single(named("sygicTravelHttpClient")) {
		val builder = OkHttpClient.Builder()
			.addInterceptor(HeadersInterceptor(
				authStorageService = { get<AuthStorageService>() },
				apiKey = getProperty("apiKey"),
				userAgent = UserAgentUtil.createUserAgent(get<Context>())
			))
			.addInterceptor(get<LocaleInterceptor>())
			.addInterceptor(get<TimeoutInterceptor>())
			.addInterceptor(get<HttpLoggingInterceptor>())
			.readTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)

		if (getProperty("httpCacheEnabled").toBoolean()) {
			builder.cache(get<Cache>())
		}

		builder.build()
	}
	single(named("sygicTravelApiRetrofit")) {
		Retrofit.Builder()
			.client(get<OkHttpClient>(named("sygicTravelHttpClient")))
			.baseUrl(getProperty("sygicTravelApiUrl") + "/$LOCALE_PLACEHOLDER/")
			.addConverterFactory(MoshiConverterFactory.create(get<Moshi>()).withNullSerialization())
			.build()
	}
	single {
		get<Retrofit>(named("sygicTravelApiRetrofit")).create(SygicTravelApiClient::class.java)
	}
}
