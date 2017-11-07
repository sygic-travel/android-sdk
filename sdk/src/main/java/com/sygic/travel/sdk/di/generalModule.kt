package com.sygic.travel.sdk.di

import android.content.Context
import android.content.SharedPreferences
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor

internal val generalModule = Kodein.Module {
	bind<Cache>() with singleton {
		val cacheFile = instance<Context>().cacheDir
		Cache(cacheFile, 10 * 1024 * 1024L) // 10 MB Cache
	}

	bind<Gson>() with singleton {
		GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.serializeNulls()
			.create()
	}

	bind<HttpLoggingInterceptor>() with singleton {
		HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
	}

	bind<SharedPreferences>() with singleton {
		instance<Context>().getSharedPreferences("SygicTravelSdk", 0)
	}
}
