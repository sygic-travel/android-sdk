package com.sygic.travel.sdk.contentProvider.api.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.*;

/**
 * <p>Implements {@link okhttp3.Interceptor}, adds headers to a requests.</p>
 */
public class HeadersInterceptor implements Interceptor {
	private String apiKey;
	private String userAgent;

	/**
	 * <p>Sets API key</p>
	 * @param apiKey API keyto be sent as a header in every request.
	 */
	public void setApiKey(String apiKey){
		this.apiKey = apiKey;
	}

	/**
	 * <p>Sets UserAgent</p>
	 * @param userAgent UserAgent to by sent as a header in every request.
	 */
	public void setUserAgent(String userAgent){
		this.userAgent = userAgent;
	}

	/**
	 * <p>Modifies the original request by adding an <b>API key</b>.</p>
	 */
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request original = chain.request();
		Request request = original.newBuilder()
			.addHeader(H_NAME_CONTENT_TYPE, H_VALUE_CONTENT_TYPE)
			.addHeader(H_NAME_API_KEY, apiKey)
			.addHeader(H_NAME_USER_AGENT, userAgent)
			.method(original.method(), original.body())
			.build();
		return chain.proceed(request);
	}
}
