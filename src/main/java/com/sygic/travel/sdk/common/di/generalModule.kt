package com.sygic.travel.sdk.common.di

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.common.api.model.ApplicationJsonAdapterFactory
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val generalModule = Kodein.Module("generalModule") {
	bind<Cache>() with singleton {
		val cacheFile = instance<Context>().cacheDir
		Cache(cacheFile, instance<Long>("httpCacheSize"))
	}

	bind<HttpLoggingInterceptor>() with singleton {
		HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
	}

	bind<SharedPreferences>() with singleton {
		instance<Context>().getSharedPreferences("SygicTravelSdk", 0)
	}

	bind<Moshi>() with singleton {
		Moshi.Builder().add(ApplicationJsonAdapterFactory).build()
	}
}
