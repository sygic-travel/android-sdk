package com.sygic.travel.sdk.common.di

import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.session.api.SygicSsoApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal val sygicAuthApiModule = module {
	single("sygicAuthHttpClient") {
		val builder = OkHttpClient.Builder()

		if (getProperty("debugMode")) {
			builder.addInterceptor(get<HttpLoggingInterceptor>())
		}

		builder.build()
	}
	single("sygicAuthApiRetrofit") {
		Retrofit.Builder()
			.client(get<OkHttpClient>("sygicAuthHttpClient"))
			.baseUrl(getProperty<String>("sygicAuthUrl"))
			.addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
			.build()
	}
	single {
		get<Retrofit>("sygicAuthApiRetrofit").create(SygicSsoApiClient::class.java)
	}
}
