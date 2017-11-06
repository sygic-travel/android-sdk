package com.sygic.travel.sdk

interface SdkConfig {
	val clientId: String

	val apiKey: String

	val debugMode: Boolean
		get() = BuildConfig.DEBUG

	val sygicAuthUrl: String
		get() = "https://auth.sygic.com"

	val sygicTravelApiUrl: String
		get() = "https://api.sygictravelapi.com/1.0"
}
