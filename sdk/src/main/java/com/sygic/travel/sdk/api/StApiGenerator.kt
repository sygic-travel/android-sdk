package com.sygic.travel.sdk.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.sygic.travel.sdk.api.StApiConstants.API_BASE_URL
import com.sygic.travel.sdk.api.StApiConstants.VERSION_AND_LOCALE
import com.sygic.travel.sdk.api.interceptors.HeadersInterceptor
import com.sygic.travel.sdk.api.interceptors.LocaleInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * Generates an implementation of an API interface, which has to follow the Retrofit patterns.
 * @see StApi
 */
internal object StApiGenerator {
    var headersInterceptor = HeadersInterceptor()
    var localeInterceptor = LocaleInterceptor()

    private val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    private var httpClient: OkHttpClient? = null

    /**
     *
     * Generates an implementation of the API endpoints.
     * @param apiClass [StApi] ought to be used unless a custom interface was created.
     * *
     * @param cacheDir Directory for request cache.
     * *
     * @param <S> Generic type of API interface.
     * *
     * @return An implementation of the API endpoints defined by the `apiClass` interface.
    </S> */
    fun <S> createStApi(apiClass: Class<S>, cacheDir: File): S {
        return buildRetrofit(cacheDir).create(apiClass)
    }

    private fun buildRetrofit(cacheDir: File): Retrofit {
        return builder.client(getHttpClient(cacheDir)).build()
    }

    private val apiGson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    private val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL + VERSION_AND_LOCALE + "/")
            .addConverterFactory(GsonConverterFactory.create(apiGson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

    private fun getHttpClient(cacheDir: File): OkHttpClient? {
        if (httpClient == null) {
            httpClient = OkHttpClient().newBuilder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(localeInterceptor)
                    .addInterceptor(headersInterceptor)
                    .cache(Cache(cacheDir, (10 * 1024 * 1024).toLong()))
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()
        }
        return httpClient
    }
}
