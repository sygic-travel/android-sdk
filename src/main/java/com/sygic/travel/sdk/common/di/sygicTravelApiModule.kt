package com.sygic.travel.sdk.common.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.common.Language
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.interceptors.HeadersInterceptor
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor.Companion.LOCALE_PLACEHOLDER
import com.sygic.travel.sdk.session.service.AuthStorageService
import com.sygic.travel.sdk.utils.UserAgentUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal val sygicTravelApiModule = Kodein.Module("sygicTravelApiModule") {
	bind<HeadersInterceptor>() with singleton {
		HeadersInterceptor(
			authStorageService = provider<AuthStorageService>(),
			apiKey = instance("apiKey"),
			userAgent = UserAgentUtil.createUserAgent(instance<Context>())
		)
	}

	bind<LocaleInterceptor>() with singleton {
		LocaleInterceptor(instance<Language>("defaultLanguage"))
	}

	bind<OkHttpClient>("sygicTravelHttpClient") with singleton {
		val builder = OkHttpClient.Builder()
			.addInterceptor(instance<HeadersInterceptor>())
			.addInterceptor(instance<LocaleInterceptor>())

		if (instance("debugMode")) {
			builder.addInterceptor(instance<HttpLoggingInterceptor>())
		}

		if (instance<Boolean>("httpCacheEnabled")) {
			builder
				.cache(instance<Cache>())
		}

		builder
			.readTimeout(60, TimeUnit.SECONDS)
			.build()
	}

	bind<Retrofit>("sygicTravelApiRetrofit") with singleton {
		Retrofit.Builder()
			.client(instance<OkHttpClient>("sygicTravelHttpClient"))
			.baseUrl(instance<String>("sygicTravelApiUrl") + "/$LOCALE_PLACEHOLDER/")
			.addConverterFactory(MoshiConverterFactory.create(instance<Moshi>()).withNullSerialization())
			.build()
	}

	bind<SygicTravelApiClient>() with singleton {
		instance<Retrofit>("sygicTravelApiRetrofit").create(SygicTravelApiClient::class.java)
	}
}
