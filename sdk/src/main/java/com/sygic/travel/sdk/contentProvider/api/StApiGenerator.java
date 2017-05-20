package com.sygic.travel.sdk.contentProvider.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sygic.travel.sdk.contentProvider.api.interceptors.HeadersInterceptor;
import com.sygic.travel.sdk.contentProvider.api.interceptors.LocaleInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.API_BASE_URL;
import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.VERSION_AND_LOCALE;

/**
 * <p>Generates an implementation of an API interface, which has to follow the Retrofit patterns.</p>
 * @see StApi
 */
public class StApiGenerator {
	public static HeadersInterceptor headersInterceptor = new HeadersInterceptor();
	public static LocaleInterceptor localeInterceptor = new LocaleInterceptor();

	private static Interceptor loggingInterceptor = new HttpLoggingInterceptor()
		.setLevel(HttpLoggingInterceptor.Level.BODY);
	private static OkHttpClient httpClient;

	/**
	 * <p>Generates an implementation of the API endpoints.</p>
	 * @param apiClass {@link StApi} ought to be used unless a custom interface was created.
	 * @param cacheDir Directory for request cache.
	 * @param <S> Generic type of API interface.
	 * @return An implementation of the API endpoints defined by the {@code apiClass} interface.
	 */
	public static <S> S createStApi(Class<S> apiClass, File cacheDir) {
		return buildRetrofit(cacheDir).create(apiClass);
	}

	private static Retrofit buildRetrofit(File cacheDir){
		return builder.client(getHttpClient(cacheDir)).build();
	}

	private static Gson apiGson = new GsonBuilder()
		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		.create();

	private static Retrofit.Builder builder =
		new Retrofit.Builder()
			.baseUrl(API_BASE_URL + VERSION_AND_LOCALE + "/")
			.addConverterFactory(GsonConverterFactory.create(apiGson))
			.addCallAdapterFactory(RxJavaCallAdapterFactory.create());

	private static OkHttpClient getHttpClient(File cacheDir) {
		if(httpClient == null) {
			httpClient = new OkHttpClient().newBuilder()
				.addInterceptor(loggingInterceptor)
				.addInterceptor(localeInterceptor)
				.addInterceptor(headersInterceptor)
				.cache(new Cache(cacheDir, 10 * 1024 * 1024))
				.readTimeout(60, TimeUnit.SECONDS)
				.build();
		}
		return httpClient;
	}
}
