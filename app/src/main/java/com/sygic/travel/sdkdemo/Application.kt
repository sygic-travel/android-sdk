package com.sygic.travel.sdkdemo

import com.sygic.travel.sdk.Sdk

class Application : android.app.Application() {
	lateinit var sdk: Sdk

	override fun onCreate() {
		super.onCreate()
		sdk = Sdk(getString(R.string.client_id), getString(R.string.api_key), this)
	}
}
