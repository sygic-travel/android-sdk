package com.sygic.travel.sdk.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.gson.Gson
import com.sygic.travel.sdk.auth.api.SygicTravelAuthApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val sygicAuthApiModule = Kodein.Module {
	bind<OkHttpClient>("sygicAuthHttpClient") with singleton {
		val builder = OkHttpClient.Builder()

		if (instance("isInDebugMode")) {
			builder.addInterceptor(instance<HttpLoggingInterceptor>())
		}
		builder.build()
	}

	bind<Retrofit>("sygicAuthApiRetrofit") with singleton {
		Retrofit.Builder()
			.client(instance("sygicAuthHttpClient"))
			.baseUrl(instance<String>("sygicAuthUrl"))
			.addConverterFactory(GsonConverterFactory.create(instance<Gson>()))
			.build()
	}

	bind<SygicTravelAuthApiClient>() with singleton {
		instance<Retrofit>("sygicAuthApiRetrofit").create(SygicTravelAuthApiClient::class.java)
	}
}
