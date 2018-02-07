package com.sygic.travel.sdk.session.facade

import com.sygic.travel.sdk.session.AuthenticationResponseCode
import com.sygic.travel.sdk.session.RegistrationResponseCode
import com.sygic.travel.sdk.session.ResetPasswordResponseCode
import com.sygic.travel.sdk.session.model.Session
import com.sygic.travel.sdk.session.service.SessionService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class SessionFacade internal constructor(
	private val sessionService: SessionService
) {
	internal val onSignOut = arrayListOf<() -> Unit>()

	fun loginUserWithPassword(username: String, password: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithPassword(username, password)
	}

	fun loginUserWithGoogle(googleToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithGoogleToken(googleToken)
	}

	fun loginUserWithFacebook(facebookToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithFacebookToken(facebookToken)
	}

	fun loginUserWithDeviceId(): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithDeviceId()
	}

	fun loginUserWithJwtToken(jwtToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithJwtToken(jwtToken)
	}

	fun registerUser(name: String, email: String, password: String): RegistrationResponseCode {
		checkNotRunningOnMainThread()
		return sessionService.register(name, email, password)
	}

	fun resetPassword(email: String): ResetPasswordResponseCode {
		checkNotRunningOnMainThread()
		return sessionService.resetPassword(email)
	}

	fun logoutUser() {
		checkNotRunningOnMainThread()
		sessionService.logout()
		onSignOut.forEach { it.invoke() }
	}

	fun getSession(): Session? {
		return sessionService.getUserSession()
	}

	private fun checkEmptySession() {
		if (getSession() != null) {
			throw IllegalStateException("Sygic Travel SDK has already an initialized session. Sign out the session first.")
		}
	}
}
