package com.sygic.travel.sdk

interface UrlConfig {
	val sygicAuthUrl: String
		get() = "https://auth.sygic.com"

	val sygicTravelApiUrl: String
		get() = "https://api.sygictravelapi.com/1.0"
}
