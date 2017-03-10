package com.sygic.travel.sdk.contentProvider.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sygic.travel.sdk.StEnvironment;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by michal.murin on 16.2.2017.
 */

public class ApiClient {
	public static final String API_VERSION = "v2.2";

	public static final String API_BASE_URL = "https://api.sygictraveldata.com/";
	public static final String API_BASE_URL_ALPHA = "https://alpha-api.sygictraveldata.com/";

	public static final String API_BASE_URL_CDN = "https://api-cdn.sygictraveldata.com/";
	public static final String API_BASE_URL_CDN_ALPHA = "https://alpha-api-cdn.sygictraveldata.com/";

	public static final String API_PHOTO_URL = "https://media-cdn.sygictraveldata.com/photo/";
	public static final String API_PHOTO_URL_ALPHA = "https://alpha-media-cdn.sygictraveldata.com/photo/";

	public static final String MEDIA_API_URL = "https://media-cdn.sygictraveldata.com";
	public static final String MEDIA_API_URL_ALPHA = "https://alpha-media-cdn.sygictraveldata.com";

	public static final String API_PHOTO_URL_HOST = "https://media-cdn.sygictraveldata.com";
	public static final String API_PHOTO_URL_ALPHA_HOST = "https://alpha-media-cdn.sygictraveldata.com";

	public static final String DEFAULT_API_KEY = "1c23f93fabebecf563b81676b3f47932";

	public static final String CONTENT_TYPE_JSON = "Content-Type:application/json";
	
	public static final String BASE_URL = "http://api.themoviedb.org/3/";
	private static Retrofit retrofit = null;


	public static <S> S createService(Class<S> serviceClass) {
		return buildRetrofit().create(serviceClass);
	}

	public static <S> S createServiceCdn(Class<S> serviceClass) {
		return buildRetrofitCDN().create(serviceClass);
	}

	public static Retrofit buildRetrofit(){
		return builder.client(httpClient).build();
	}

	public static Retrofit buildRetrofitCDN(){
		return builderCDN.client(httpClient).build();
	}

	private static Interceptor loggingInterceptor = new HttpLoggingInterceptor()
		.setLevel(getHttpLoggingInterceptorLevel());

//	private static Interceptor userAgentInterceptor = new Interceptor() {
//		@Override
//		public Response intercept(Chain chain) throws IOException {
//			Request original = chain.request();
//
//			Request request = original.newBuilder()
//				.header("User-Agent", "Sygic Travel Android")
//				.method(original.method(), original.body())
//				.build();
//
//			return chain.proceed(request);
//		}
//	};

	public static LocaleInterceptor localeInterceptor = new LocaleInterceptor();

	private static OkHttpClient httpClient = new OkHttpClient().newBuilder()
		.addInterceptor(loggingInterceptor)
//		.addInterceptor(userAgentInterceptor)
		.addInterceptor(localeInterceptor)
//		.cache(new Cache(SygicTravel.getInstance().getCacheDir(), 10 * 1024 * 1024))
		.readTimeout(60, TimeUnit.SECONDS)
		.build();

	private static Gson apiGson = new GsonBuilder()
		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		.create();

	//TO_INTERCEPT is replaced by api version and locale in LocaleInterceptor class

	private static Retrofit.Builder builder = new Retrofit.Builder()
		.baseUrl((StEnvironment.alpha ? API_BASE_URL_ALPHA : API_BASE_URL) + LocaleInterceptor.TO_INTERCEPT + "/")
		.addConverterFactory(GsonConverterFactory.create(apiGson));

	private static Retrofit.Builder builderCDN = new Retrofit.Builder()
		.baseUrl((StEnvironment.alpha ? API_BASE_URL_CDN_ALPHA : API_BASE_URL_CDN) + LocaleInterceptor.TO_INTERCEPT + "/")
		.addConverterFactory(GsonConverterFactory.create(apiGson));

	private static HttpLoggingInterceptor.Level getHttpLoggingInterceptorLevel() {
		if (StEnvironment.debug) {
			return HttpLoggingInterceptor.Level.BODY;
		} else {
			return HttpLoggingInterceptor.Level.NONE;
		}
	}
}
