package com.sygic.travel.sdk.common.di

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import timber.log.Timber

internal val generalModule = module {
	single {
		val cacheFile = get<Context>().cacheDir
		Cache(cacheFile, getProperty("httpCacheSize").toLong())
	}
	single {
		return@single HttpLoggingInterceptor(
			object : HttpLoggingInterceptor.Logger {
				override fun log(message: String) {
					Timber.tag("OkHttp").d(message)
				}
			}
		).apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
	}
	single {
		get<Context>().getSharedPreferences("SygicTravelSdk", 0)
	}
	single {
		Moshi.Builder().build()
	}
}
