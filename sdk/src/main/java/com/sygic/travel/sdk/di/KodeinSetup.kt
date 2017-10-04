package com.sygic.travel.sdk.di

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.github.salomonbrys.kodein.with
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sygic.travel.sdk.BuildConfig
import com.sygic.travel.sdk.api.StApi
import com.sygic.travel.sdk.api.StApiConstants.VERSION_AND_LOCALE
import com.sygic.travel.sdk.api.interceptors.HeadersInterceptor
import com.sygic.travel.sdk.api.interceptors.LocaleInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object KodeinSetup {
	private val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB Cache
	private val PLACES_API_URL = "https://api.sygictravelapi.com/$VERSION_AND_LOCALE/"

	fun setupKodein(context: Context, xApiKey: String) = Kodein {
		constant("apiKey") with xApiKey
		bind<Context>() with singleton { context }

		import(networkModule)
		import(placesModule)
	}

	private val networkModule = Kodein.Module {
		bind<Cache>() with singleton {
			val cacheFile = instance<Context>().cacheDir
			Cache(cacheFile, CACHE_SIZE)
		}

		bind<Gson>() with singleton {
			GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create()
		}

		bind<HttpLoggingInterceptor>() with singleton {
			HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
		}
	}

	private val placesModule = Kodein.Module {
		bind<HeadersInterceptor>() with singleton {
			HeadersInterceptor(apiKey = instance("apiKey"), userAgent = createUserAgent(instance()))
		}

		bind<LocaleInterceptor>() with singleton { LocaleInterceptor() }

		bind<OkHttpClient>("placesHttpClient") with singleton {
			val builder = OkHttpClient.Builder()

			if (BuildConfig.DEBUG) {
				builder.addInterceptor(instance<HttpLoggingInterceptor>())
			}

			builder.addInterceptor(instance<HeadersInterceptor>())
				.addInterceptor(instance<LocaleInterceptor>())
				.cache(instance())
				.readTimeout(60, TimeUnit.SECONDS)
				.build()
		}

		bind<Retrofit>("placesRetrofit") with singleton {
			Retrofit.Builder()
				.client(instance("placesHttpClient"))
				.baseUrl(PLACES_API_URL)
				.addConverterFactory(GsonConverterFactory.create(instance()))
				.build()
		}

		bind<StApi>() with singleton {
			instance<Retrofit>("placesRetrofit").create(StApi::class.java)
		}
	}

	private fun createUserAgent(context: Context): String {
		var packageInfo: PackageInfo? = null
		try {
			packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
		} catch (e: PackageManager.NameNotFoundException) {
			e.printStackTrace()
		}

		return StringBuilder()
			.append(context.packageName).append("/")
			.append(if (packageInfo != null) packageInfo.versionName + " " else "")
			.append("sygic-travel-sdk-android/sdk-version").append(" ")
			.append("Android/").append(Build.VERSION.RELEASE).toString()
	}
}
