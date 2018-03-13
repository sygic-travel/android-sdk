package com.sygic.travel.sdk.session.service

import android.content.SharedPreferences
import java.util.Date
import java.util.UUID

internal class AuthStorageService(private val sharedPreferences: SharedPreferences) {
	companion object {
		private const val CLIENT_SESSION_KEY = "auth.client_session_key"
		private const val USER_SESSION_KEY = "auth.user_session_key"
		private const val REFRESH_TOKEN_KEY = "auth.refresh_token_key"
		private const val EXPIRES_IN_TIMESTAMP = "auth.expires_in_timestamp"
		private const val SUGGESTED_REFRESH_TIMESTAMP = "auth.suggested_refresh_timestamp"
		private const val DEVICE_ID = "auth.device_id"
	}

	fun setClientSession(accessToken: String?) {
		val editor = sharedPreferences.edit()
		editor.putString(CLIENT_SESSION_KEY, accessToken)
		editor.apply()
	}

	fun getClientSession(): String? {
		return sharedPreferences.getString(CLIENT_SESSION_KEY, null)
	}

	fun getDeviceId(): String? {
		val editor = sharedPreferences.edit()
		var deviceId = sharedPreferences.getString(DEVICE_ID, null)
		if (deviceId == null) {
			val uuid = UUID.randomUUID().toString()
			editor.putString(DEVICE_ID, uuid)
			editor.apply()
			deviceId = uuid
		}
		return deviceId
	}

	fun getUserSession(): String? {
		return sharedPreferences.getString(USER_SESSION_KEY, null)
	}

	fun getSuggestedRefreshTime(): Long {
		return sharedPreferences.getLong(SUGGESTED_REFRESH_TIMESTAMP, 0)
	}

	fun getExpirationTime(): Long {
		return sharedPreferences.getLong(EXPIRES_IN_TIMESTAMP, 0)
	}

	fun getRefreshToken(): String? {
		return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
	}

	fun setUserSession(accessToken: String?, refreshToken: String?, secondsToExpiration: Long) {
		// Add (1000 * secondsToExpiration) to current time will and we'll have exact token
		// expiration time.
		// We suggest to set refresh interval in 1/4th of total token expiration time,
		// so that we add (1000/4 * secondsToExpiration) to current time in millis.
		val now = Date().time

		val editor = sharedPreferences.edit()
		editor.putString(USER_SESSION_KEY, accessToken)
		editor.putString(REFRESH_TOKEN_KEY, refreshToken)
		editor.putLong(EXPIRES_IN_TIMESTAMP, now + 1000 * secondsToExpiration)
		editor.putLong(SUGGESTED_REFRESH_TIMESTAMP, now + 250 * secondsToExpiration)
		editor.apply()
	}
}
