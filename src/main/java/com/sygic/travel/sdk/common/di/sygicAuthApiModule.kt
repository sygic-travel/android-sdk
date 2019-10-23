package com.sygic.travel.sdk.common.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.common.api.interceptors.HeadersInterceptor
import com.sygic.travel.sdk.session.api.SygicSsoApiClient
import com.sygic.travel.sdk.utils.UserAgentUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal val sygicAuthApiModule = module {
	single(named("sygicAuthHttpClient")) {
		OkHttpClient.Builder()
			.addInterceptor(HeadersInterceptor(
				authStorageService = null,
				apiKey = null,
				userAgent = UserAgentUtil.createUserAgent(get<Context>())
			))
			.addInterceptor(get<HttpLoggingInterceptor>())
			.build()
	}
	single(named("sygicAuthApiRetrofit")) {
		Retrofit.Builder()
			.client(get<OkHttpClient>(named("sygicAuthHttpClient")))
			.baseUrl(getProperty<String>("sygicAuthUrl"))
			.addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
			.build()
	}
	single {
		get<Retrofit>(named("sygicAuthApiRetrofit")).create(SygicSsoApiClient::class.java)
	}
}
