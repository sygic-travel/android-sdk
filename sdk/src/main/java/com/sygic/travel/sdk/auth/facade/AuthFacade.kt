package com.sygic.travel.sdk.auth.facade

import com.sygic.travel.sdk.Callback
import com.sygic.travel.sdk.auth.AuthenticationResponseCode
import com.sygic.travel.sdk.auth.RegistrationResponseCode
import com.sygic.travel.sdk.auth.model.UserSession
import com.sygic.travel.sdk.auth.service.AuthService
import com.sygic.travel.sdk.utils.runAsync
import com.sygic.travel.sdk.utils.runWithCallback

class AuthFacade(
	private val authService: AuthService
) {

	fun loginUserWithPassword(username: String, password: String, callback: Callback<AuthenticationResponseCode>) {
		runWithCallback({ authService.authWithPassword(username, password) }, callback)
	}

	suspend fun loginUserWithPassword(username: String, password: String): AuthenticationResponseCode {
		return runAsync { authService.authWithPassword(username, password) }
	}

	fun loginUserWithGoogle(googleToken: String, callback: Callback<AuthenticationResponseCode>) {
		runWithCallback({ authService.authWithGoogleToken(googleToken) }, callback)
	}

	suspend fun loginUserWithGoogle(googleToken: String): AuthenticationResponseCode {
		return runAsync { authService.authWithGoogleToken(googleToken) }
	}

	fun loginUserWithFacebook(facebookToken: String, callback: Callback<AuthenticationResponseCode>) {
		runWithCallback({ authService.authWithFacebookToken(facebookToken) }, callback)
	}

	suspend fun loginUserWithFacebook(facebookToken: String): AuthenticationResponseCode {
		return runAsync { authService.authWithFacebookToken(facebookToken) }
	}

	fun loginUserWithDeviceId(callback: Callback<AuthenticationResponseCode>) {
		runWithCallback({ authService.authWithDeviceId() }, callback)
	}

	suspend fun loginUserWithDeviceId(): AuthenticationResponseCode {
		return runAsync { authService.authWithDeviceId() }
	}

	fun loginUserWithJwtToken(jwtToken: String, callback: Callback<AuthenticationResponseCode>) {
		runWithCallback({ authService.authWithJwtToken(jwtToken) }, callback)
	}

	suspend fun loginUserWithJwtToken(jwtToken: String): AuthenticationResponseCode {
		return runAsync { authService.authWithJwtToken(jwtToken) }
	}

	fun registerUser(name: String, email: String, password: String, callback: Callback<RegistrationResponseCode>) {
		runWithCallback({ authService.register(name, email, password) }, callback)
	}

	suspend fun registerUser(name: String, email: String, password: String): RegistrationResponseCode {
		return runAsync { authService.register(name, email, password) }
	}

	fun logoutUser() {
		authService.logout()
	}

	fun getUserSession(): UserSession? {
		return authService.getUserSession()
	}
}
