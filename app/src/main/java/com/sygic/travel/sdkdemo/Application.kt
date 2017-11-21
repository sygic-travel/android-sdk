package com.sygic.travel.sdkdemo

import com.sygic.travel.sdk.Sdk
import com.sygic.travel.sdk.SdkConfig

class Application : android.app.Application() {
	lateinit var sdk: Sdk

	override fun onCreate() {
		super.onCreate()
		sdk = Sdk(
			applicationContext,
			object : SdkConfig() {
				override val clientId
					get() = getString(R.string.client_id)

				override val apiKey
					get() = getString(R.string.api_key)
			}
		)
	}
}
