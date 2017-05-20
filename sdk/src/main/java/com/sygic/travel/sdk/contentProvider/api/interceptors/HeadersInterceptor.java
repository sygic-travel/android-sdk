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
	private String xApiKey;

	/**
	 * <p>Updates API key</p>
	 * @param xApiKey new API key, which will be used in API requests
	 */
	public void updateXApiKey(String xApiKey){
		this.xApiKey = xApiKey;
	}

	/**
	 * <p>Modifies the original request by adding an <b>API key</b>.</p>
	 */
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request original = chain.request();
		Request request = original.newBuilder()
			.addHeader(H_NAME_CONTENT_TYPE, H_VALUE_CONTENT_TYPE)
			.addHeader(H_NAME_API_KEY, xApiKey)
			.method(original.method(), original.body())
			.build();
		return chain.proceed(request);
	}
}
