package com.sygic.travel.sdk.contentProvider.api.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>Implements {@link okhttp3.Interceptor}, adds API key to requests.</p>
 */
public class AuthorizationInterceptor implements Interceptor {
	private static final String X_API_KEY = "x-api-key";

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
		if(xApiKey != null && !xApiKey.equals("")) {
			Request request = original.newBuilder()
				.addHeader(X_API_KEY, xApiKey)
				.method(original.method(), original.body())
				.build();
			return chain.proceed(request);
		} else {
			return chain.proceed(original);
		}
	}
}
