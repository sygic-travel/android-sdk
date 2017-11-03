package com.sygic.travel.sdk.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.google.gson.Gson
import com.sygic.travel.sdk.auth.service.AuthStorageService
import com.sygic.travel.sdk.common.api.interceptors.HeadersInterceptor
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor.Companion.LOCALE_PLACEHOLDER
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.utils.UserAgentUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal val sygicTravelApiModule = Kodein.Module {
	bind<HeadersInterceptor>() with singleton {
		HeadersInterceptor(
			authStorageService = provider<AuthStorageService>(),
			apiKey = instance("apiKey"),
			userAgent = UserAgentUtil.createUserAgent(instance<Context>())
		)
	}

	bind<LocaleInterceptor>() with singleton { LocaleInterceptor() }

	bind<OkHttpClient>("sygicTravelHttpClient") with singleton {
		val builder = OkHttpClient.Builder()

		if (instance("debugMode")) {
			builder.addNetworkInterceptor(instance<HttpLoggingInterceptor>())
		}

		builder.addInterceptor(instance<HeadersInterceptor>())
			.addInterceptor(instance<LocaleInterceptor>())
			.cache(instance<Cache>())
			.readTimeout(60, TimeUnit.SECONDS)
			.build()
	}

	bind<Retrofit>("sygicTravelApiRetrofit") with singleton {
		Retrofit.Builder()
			.client(instance<OkHttpClient>("sygicTravelHttpClient"))
			.baseUrl(instance<String>("sygicTravelApiUrl") + "/$LOCALE_PLACEHOLDER/")
			.addConverterFactory(GsonConverterFactory.create(instance<Gson>()))
			.build()
	}

	bind<SygicTravelApiClient>() with singleton {
		instance<Retrofit>("sygicTravelApiRetrofit").create(SygicTravelApiClient::class.java)
	}
}
