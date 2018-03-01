package com.sygic.travel.sdk

abstract class SdkConfig {
	abstract val apiKey: String

	open val clientId: String?
		get() = null

	open val debugMode: Boolean
		get() = BuildConfig.DEBUG

	open val sygicAuthUrl: String
		get() = "https://auth.sygic.com"

	open val sygicTravelApiUrl: String
		get() = "https://api.sygictravelapi.com/1.0"
}
