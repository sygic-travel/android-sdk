package com.sygic.travel.sdk.auth.facade

import com.sygic.travel.sdk.auth.AuthenticationResponseCode
import com.sygic.travel.sdk.auth.RegistrationResponseCode
import com.sygic.travel.sdk.auth.ResetPasswordResponseCode
import com.sygic.travel.sdk.auth.model.UserSession
import com.sygic.travel.sdk.auth.service.AuthService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class AuthFacade(
	private val authService: AuthService
) {
	internal val onSignOut = arrayListOf<() -> Unit>()

	fun loginUserWithPassword(username: String, password: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		return authService.authWithPassword(username, password)
	}

	fun loginUserWithGoogle(googleToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		return authService.authWithGoogleToken(googleToken)
	}

	fun loginUserWithFacebook(facebookToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		return authService.authWithFacebookToken(facebookToken)
	}

	fun loginUserWithDeviceId(): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		return authService.authWithDeviceId()
	}

	fun loginUserWithJwtToken(jwtToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		return authService.authWithJwtToken(jwtToken)
	}

	fun registerUser(name: String, email: String, password: String): RegistrationResponseCode {
		checkNotRunningOnMainThread()
		return authService.register(name, email, password)
	}

	fun resetPassword(email: String): ResetPasswordResponseCode {
		checkNotRunningOnMainThread()
		return authService.resetPassword(email)
	}

	fun logoutUser() {
		checkNotRunningOnMainThread()
		authService.logout()
		onSignOut.forEach { it.invoke() }
	}

	fun getUserSession(): UserSession? {
		return authService.getUserSession()
	}
}
