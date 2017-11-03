package com.sygic.travel.sdk

interface SdkConfig {
	val clientId: String

	val apiKey: String

	val sygicAuthUrl: String
		get() = "https://auth.sygic.com"

	val sygicTravelApiUrl: String
		get() = "https://api.sygictravelapi.com/1.0"
}
