package com.sygic.travel.sdk.common.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.interceptors.HeadersInterceptor
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor.Companion.LOCALE_PLACEHOLDER
import com.sygic.travel.sdk.session.service.AuthStorageService
import com.sygic.travel.sdk.utils.UserAgentUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal val sygicTravelApiModule = module {
	single {
		HeadersInterceptor(
			authStorageService = { get<AuthStorageService>() },
			apiKey = getProperty("apiKey"),
			userAgent = UserAgentUtil.createUserAgent(get<Context>())
		)
	}
	single {
		LocaleInterceptor(getProperty("defaultLanguage"))
	}
	single("sygicTravelHttpClient") {
		val builder = OkHttpClient.Builder()
			.addInterceptor(get<HeadersInterceptor>())
			.addInterceptor(get<LocaleInterceptor>())
		if (getProperty("debugMode")) {
			builder.addInterceptor(get<HttpLoggingInterceptor>())
		}
		if (getProperty("httpCacheEnabled")) {
			builder
				.cache(get<Cache>())
		}
		builder
			.readTimeout(60, TimeUnit.SECONDS)
			.build()
	}
	single("sygicTravelApiRetrofit") {
		Retrofit.Builder()
			.client(get<OkHttpClient>("sygicTravelHttpClient"))
			.baseUrl(getProperty<String>("sygicTravelApiUrl") + "/$LOCALE_PLACEHOLDER/")
			.addConverterFactory(MoshiConverterFactory.create(get<Moshi>()).withNullSerialization())
			.build()
	}
	single {
		get<Retrofit>("sygicTravelApiRetrofit").create(SygicTravelApiClient::class.java)
	}
}
