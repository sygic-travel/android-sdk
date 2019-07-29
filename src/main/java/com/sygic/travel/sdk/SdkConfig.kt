package com.sygic.travel.sdk

import com.sygic.travel.sdk.common.Language

abstract class SdkConfig {
	abstract val apiKey: String

	open val clientId: String?
		get() = null

	open val sygicAuthUrl: String
		get() = "https://auth.sygic.com"

	open val sygicTravelApiUrl: String
		get() = "https://api.sygictravelapi.com/1.1"

	open val httpCacheEnabled: Boolean
		get() = true

	open val httpCacheSize: Long
		get() = 10 * 1024 * 1024L // 10 MB Cache

	open var language: Language = Language.EN
}
