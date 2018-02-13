package com.sygic.travel.sdk.common.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sygic.travel.sdk.session.api.SygicSsoApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val sygicAuthApiModule = Kodein.Module {
	bind<OkHttpClient>("sygicAuthHttpClient") with singleton {
		val builder = OkHttpClient.Builder()

		if (instance("debugMode")) {
			builder.addInterceptor(instance<HttpLoggingInterceptor>())
		}

		builder.build()
	}

	bind<Gson>("sygicAuthGson") with singleton {
		GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.create()
	}

	bind<Retrofit>("sygicAuthApiRetrofit") with singleton {
		Retrofit.Builder()
			.client(instance("sygicAuthHttpClient"))
			.baseUrl(instance<String>("sygicAuthUrl"))
			.addConverterFactory(GsonConverterFactory.create(instance<Gson>("sygicAuthGson")))
			.build()
	}

	bind<SygicSsoApiClient>() with singleton {
		instance<Retrofit>("sygicAuthApiRetrofit").create(SygicSsoApiClient::class.java)
	}
}
