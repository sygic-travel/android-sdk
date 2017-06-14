package com.sygic.travel.sdk.api

import com.sygic.travel.sdk.api.StApiConstants.API_BASE_URL
import com.sygic.travel.sdk.api.StApiConstants.VERSION_AND_LOCALE
import com.sygic.travel.sdk.api.interceptors.HeadersInterceptor
import com.sygic.travel.sdk.api.interceptors.LocaleInterceptor

/**
 *
 * Generates an implementation of an API interface, which has to follow the Retrofit patterns.
 * @see StApi
 */
internal object StApiGenerator {
    var headersInterceptor = HeadersInterceptor()
    var localeInterceptor = LocaleInterceptor()

    private val loggingInterceptor = okhttp3.logging.HttpLoggingInterceptor()
            .setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY)
    private var httpClient: okhttp3.OkHttpClient? = null

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
    fun <S> createStApi(apiClass: Class<S>, cacheDir: java.io.File): S {
        return com.sygic.travel.sdk.api.StApiGenerator.buildRetrofit(cacheDir).create(apiClass)
    }

    private fun buildRetrofit(cacheDir: java.io.File): retrofit2.Retrofit {
        return com.sygic.travel.sdk.api.StApiGenerator.builder.client(com.sygic.travel.sdk.api.StApiGenerator.getHttpClient(cacheDir)).build()
    }

    private val apiGson = com.google.gson.GsonBuilder()
            .setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    private val builder = retrofit2.Retrofit.Builder()
            .baseUrl(API_BASE_URL + VERSION_AND_LOCALE + "/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(com.sygic.travel.sdk.api.StApiGenerator.apiGson))
            .addCallAdapterFactory(retrofit2.adapter.rxjava.RxJavaCallAdapterFactory.create())

    private fun getHttpClient(cacheDir: java.io.File): okhttp3.OkHttpClient? {
        if (com.sygic.travel.sdk.api.StApiGenerator.httpClient == null) {
            com.sygic.travel.sdk.api.StApiGenerator.httpClient = okhttp3.OkHttpClient().newBuilder()
                    .addInterceptor(com.sygic.travel.sdk.api.StApiGenerator.loggingInterceptor)
                    .addInterceptor(com.sygic.travel.sdk.api.StApiGenerator.localeInterceptor)
                    .addInterceptor(com.sygic.travel.sdk.api.StApiGenerator.headersInterceptor)
                    .cache(okhttp3.Cache(cacheDir, (10 * 1024 * 1024).toLong()))
                    .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                    .build()
        }
        return com.sygic.travel.sdk.api.StApiGenerator.httpClient
    }
}
