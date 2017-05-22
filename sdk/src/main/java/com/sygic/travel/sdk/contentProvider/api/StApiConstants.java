package com.sygic.travel.sdk.contentProvider.api;

/**
 * <p>Constants used for SDK configuration.</p>
 */
public class StApiConstants {

	// HEADERS
	public static final String H_NAME_API_KEY = "x-api-key";

	public static final String H_NAME_CONTENT_TYPE = "Content-Type";
	public static final String H_VALUE_CONTENT_TYPE = "application/json";

	public static final String H_NAME_USER_AGENT = "User-Agent";

	// BASE URL
	static String API_BASE_URL = "https://api.sygictravelapi.com/";
	public static String API_VERSION = "0.2";

	// PATTERNS
	public static final String VERSION_AND_LOCALE = "[api_version_and_locale]";
}
