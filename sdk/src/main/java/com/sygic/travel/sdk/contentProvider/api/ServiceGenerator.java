package com.sygic.travel.sdk.contentProvider.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sygic.travel.sdk.StEnvironment;
import com.sygic.travel.sdk.StSDK;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sygic.travel.sdk.contentProvider.api.ApiConstants.API_BASE_URL;
import static com.sygic.travel.sdk.contentProvider.api.ApiConstants.API_BASE_URL_ALPHA;

public class ServiceGenerator {

//	private static final String USER_AGENT = "User-Agent";
//	private static final String STA_USER_AGENT = "Sygic Travel Android";

	//	private static Interceptor userAgentInterceptor = new Interceptor() {
//		@Override
//		public Response intercept(Chain chain) throws IOException {
//			Request original = chain.request();
//
//			Request request = original.newBuilder()
//				.addHeader(USER_AGENT, STA_USER_AGENT)
//				.method(original.method(), original.body())
//				.build();
//
//			return chain.proceed(request);
//		}
	//	};

	public static <S> S createService(Class<S> serviceClass) {
		return buildRetrofit().create(serviceClass);
	}

	public static AuthorizationInterceptor authorizationInterceptor = new AuthorizationInterceptor();

	public static LocaleInterceptor localeInterceptor = new LocaleInterceptor();

	private static Interceptor loggingInterceptor = new HttpLoggingInterceptor()
		.setLevel(getHttpLoggingInterceptorLevel());

	public static Retrofit buildRetrofit(){
		return builder.client(httpClient).build();
	}


	private static OkHttpClient httpClient = new OkHttpClient().newBuilder()
		.addInterceptor(loggingInterceptor)
		.addInterceptor(localeInterceptor)
//		.addInterceptor(userAgentInterceptor)
		.addInterceptor(authorizationInterceptor)
		.cache(new Cache(StSDK.getInstance().getCacheDir(), 10 * 1024 * 1024))
		.readTimeout(60, TimeUnit.SECONDS)
		.build();

	private static Gson apiGson = new GsonBuilder()
		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		.create();

	//TO_INTERCEPT is replaced by api version and locale in LocaleInterceptor class

	private static Retrofit.Builder builder = new Retrofit.Builder()
		.baseUrl((StEnvironment.alpha ? API_BASE_URL_ALPHA : API_BASE_URL) + LocaleInterceptor.TO_INTERCEPT + "/")
		.addConverterFactory(GsonConverterFactory.create(apiGson));

	private static HttpLoggingInterceptor.Level getHttpLoggingInterceptorLevel() {
		if (StEnvironment.debug) {
			return HttpLoggingInterceptor.Level.BODY;
		} else {
			return HttpLoggingInterceptor.Level.NONE;
		}
	}
}
