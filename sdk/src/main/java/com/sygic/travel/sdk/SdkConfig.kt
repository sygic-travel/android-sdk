package com.sygic.travel.sdk

abstract class SdkConfig {
	abstract val clientId: String

	abstract val apiKey: String

	open val debugMode: Boolean
		get() = BuildConfig.DEBUG

	open val sygicAuthUrl: String
		get() = "https://auth.sygic.com"

	open val sygicTravelApiUrl: String
		get() = "https://api.sygictravelapi.com/1.0"
}
