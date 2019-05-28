package com.sygic.travel.sdk.common.di

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

internal val generalModule = module {
	single {
		val cacheFile = get<Context>().cacheDir
		Cache(cacheFile, getProperty<Long>("httpCacheSize"))
	}
	single {
		HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
	}
	single {
		get<Context>().getSharedPreferences("SygicTravelSdk", 0)
	}
	single {
		Moshi.Builder().build()
	}
}
