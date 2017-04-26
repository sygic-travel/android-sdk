package com.sygic.travel.sdk.contentProvider.api.interceptors;

import com.sygic.travel.sdk.contentProvider.api.SupportedLanguages;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.API_VERSION;

/**
 * <p>Implements {@link okhttp3.Interceptor}, adds API version and locale code.</p>
 */
public class LocaleInterceptor implements Interceptor{
	public static final String TO_INTERCEPT = "[api_version_and_locale]";

	/**
	 * <p>Device's current locale code</p>
	 */
	private String locale;

	public LocaleInterceptor(){
		updateLocale();
	}

	/**
	 * <p>Updates {@link LocaleInterceptor#locale} to device's current locale code</p>
	 */
	public void updateLocale(){
		locale = SupportedLanguages.getActualLocale();
	}

	/**
	 * <p>Modifies the original request by adding an <b>API version</b> and a <b>locale code</b>.</p>
	 */
	@Override
	public Response intercept(Interceptor.Chain chain) throws IOException {
		Request original = chain.request();
		String url = original.url().toString();

		url = url.replace(TO_INTERCEPT, API_VERSION + "/" + locale);

		Request request = original.newBuilder()
			.url(url)
			.method(original.method(), original.body())
			.build();

		return chain.proceed(request);
	}
}
