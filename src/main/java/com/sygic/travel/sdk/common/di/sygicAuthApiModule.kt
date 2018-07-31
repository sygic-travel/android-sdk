package com.sygic.travel.sdk.common.di

import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.session.api.SygicSsoApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal val sygicAuthApiModule = Kodein.Module("sygicAuthApiModule") {
	bind<OkHttpClient>("sygicAuthHttpClient") with singleton {
		val builder = OkHttpClient.Builder()

		if (instance("debugMode")) {
			builder.addInterceptor(instance<HttpLoggingInterceptor>())
		}

		builder.build()
	}

	bind<Retrofit>("sygicAuthApiRetrofit") with singleton {
		Retrofit.Builder()
			.client(instance("sygicAuthHttpClient"))
			.baseUrl(instance<String>("sygicAuthUrl"))
			.addConverterFactory(MoshiConverterFactory.create(instance<Moshi>()))
			.build()
	}

	bind<SygicSsoApiClient>() with singleton {
		instance<Retrofit>("sygicAuthApiRetrofit").create(SygicSsoApiClient::class.java)
	}
}
