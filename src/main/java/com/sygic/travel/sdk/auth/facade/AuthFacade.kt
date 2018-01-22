package com.sygic.travel.sdk.auth.facade

import com.sygic.travel.sdk.auth.AuthenticationResponseCode
import com.sygic.travel.sdk.auth.RegistrationResponseCode
import com.sygic.travel.sdk.auth.ResetPasswordResponseCode
import com.sygic.travel.sdk.auth.model.UserSession
import com.sygic.travel.sdk.auth.service.AuthService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class AuthFacade internal constructor(
	private val authService: AuthService
) {
	internal val onSignOut = arrayListOf<() -> Unit>()

	fun loginUserWithPassword(username: String, password: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return authService.authWithPassword(username, password)
	}

	fun loginUserWithGoogle(googleToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return authService.authWithGoogleToken(googleToken)
	}

	fun loginUserWithFacebook(facebookToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return authService.authWithFacebookToken(facebookToken)
	}

	fun loginUserWithDeviceId(): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return authService.authWithDeviceId()
	}

	fun loginUserWithJwtToken(jwtToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
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

	private fun checkEmptySession() {
		if (getUserSession() != null) {
			throw IllegalStateException("Sygic Travel SDK has already an initialized user session. Logout the user first.")
		}
	}
}
