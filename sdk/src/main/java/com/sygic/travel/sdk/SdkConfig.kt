package com.sygic.travel.sdk

abstract class SdkConfig {
	abstract val clientId: String

	abstract val apiKey: String

	val debugMode: Boolean
		get() = BuildConfig.DEBUG

	val sygicAuthUrl: String
		get() = "https://auth.sygic.com"

	val sygicTravelApiUrl: String
		get() = "https://api.sygictravelapi.com/1.0"
}
