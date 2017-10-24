package com.sygic.travel.sdk.auth.facade

import com.sygic.travel.sdk.Callback
import com.sygic.travel.sdk.auth.model.UserRegistrationResponse
import com.sygic.travel.sdk.auth.model.UserSession
import com.sygic.travel.sdk.auth.service.AuthService
import com.sygic.travel.sdk.auth.service.AuthStorageService
import com.sygic.travel.sdk.utils.runAsync
import com.sygic.travel.sdk.utils.runWithCallback

class AuthFacade(
	private val authService: AuthService,
	private val authStorageService: AuthStorageService
) {

	fun loginUserWithPassword(
		username: String,
		password: String,
		callback: Callback<UserSession?>
	) {
		val request = authService.passwordAuthRequest(username, password)
		runWithCallback({ authService.authorize(request) }, callback)
	}

	suspend fun loginUserWithPassword(username: String, password: String): UserSession? {
		val request = authService.passwordAuthRequest(username, password)
		return runAsync { authService.authorize(request) }
	}

	fun loginUserWithGoogle(googleToken: String?, callback: Callback<UserSession?>) {
		val request = authService.googleAuthRequest(googleToken)
		runWithCallback({ authService.authorize(request) }, callback)
	}

	suspend fun loginUserWithGoogle(googleToken: String?): UserSession? {
		val request = authService.googleAuthRequest(googleToken)
		return runAsync { authService.authorize(request) }
	}

	fun loginUserWithFacebook(facebookToken: String?, callback: Callback<UserSession?>) {
		val request = authService.facebookAuthRequest(facebookToken)
		runWithCallback({ authService.authorize(request) }, callback)
	}

	suspend fun loginUserWithFacebook(facebookToken: String?): UserSession? {
		val request = authService.facebookAuthRequest(facebookToken)
		return runAsync { authService.authorize(request) }
	}

	fun loginUserWithDeviceId(callback: Callback<UserSession?>) {
		val authRequest = authService.deviceIdAuthRequest(authStorageService.getDeviceId())
		runWithCallback({ authService.authorize(authRequest) }, callback)
	}

	suspend fun loginUserWithDeviceId(): UserSession? {
		val authRequest = authService.deviceIdAuthRequest(authStorageService.getDeviceId())
		return runAsync { authService.authorize(authRequest) }
	}

	fun loginUserWithJwtToken(jwtToken: String, callback: Callback<UserSession?>) {
		val authRequest = authService.jwtTokenAuthRequest(jwtToken)
		runWithCallback({ authService.authorize(authRequest) }, callback)
	}

	suspend fun loginUserWithJwtToken(jwtToken: String): UserSession? {
		val authRequest = authService.jwtTokenAuthRequest(jwtToken)
		return runAsync { authService.authorize(authRequest) }
	}

	fun registerUser(
		name: String,
		email: String,
		password: String,
		callback: Callback<UserRegistrationResponse?>
	) {
		val userRegistrationRequest = authService.userRegistrationRequest(email, password, name)
		runWithCallback({ authService.register(userRegistrationRequest) }, callback)
	}

	suspend fun registerUser(
		name: String,
		email: String,
		password: String
	): UserRegistrationResponse? {
		val userRegistrationRequest = authService.userRegistrationRequest(email, password, name)
		return runAsync { authService.register(userRegistrationRequest) }
	}

	fun logoutUser() {
		authService.logout()
	}

	fun getUserSession(): UserSession? {
		return authService.getUserSession()
	}
}
