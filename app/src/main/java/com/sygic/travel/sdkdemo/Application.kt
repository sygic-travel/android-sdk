package com.sygic.travel.sdkdemo

import com.sygic.travel.sdk.StSDK

class Application : android.app.Application() {
	lateinit var stSdk: StSDK

	override fun onCreate() {
		super.onCreate()
		stSdk = StSDK.create(getString(R.string.api_key), this)
	}
}
