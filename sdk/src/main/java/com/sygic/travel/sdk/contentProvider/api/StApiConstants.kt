package com.sygic.travel.sdk.contentProvider.api

object StApiConstants {

    // HEADERS
    val H_NAME_API_KEY = "x-api-key"

    val H_NAME_CONTENT_TYPE = "Content-Type"
    val H_VALUE_CONTENT_TYPE = "application/json"

    val H_NAME_USER_AGENT = "User-Agent"

    // BASE URL
    internal var API_BASE_URL = "https://api.sygictravelapi.com/"
    var API_VERSION = "0.2"

    // PATTERNS
    val VERSION_AND_LOCALE = "[api_version_and_locale]"
}
