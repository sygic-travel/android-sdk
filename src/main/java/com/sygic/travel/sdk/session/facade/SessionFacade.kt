package com.sygic.travel.sdk.session.facade

import com.sygic.travel.sdk.session.model.Session
import com.sygic.travel.sdk.session.service.SessionService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

/**
 * Session facade handles user (or anonymous) session. Session is needed for user data synchronization.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class SessionFacade internal constructor(
	private val sessionService: SessionService
) {
	internal val onSignOut = arrayListOf<() -> Unit>()

	fun signInWithCredentials(email: String, password: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithPassword(email, password)
	}

	fun signInWithGoogleIdToken(idToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithGoogleToken(idToken)
	}

	fun signInWithFacebookAccessToken(accessToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithFacebookToken(accessToken)
	}

	fun signInWithJwtToken(jwtToken: String): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithJwtToken(jwtToken)
	}

	fun signInWithDeviceId(): AuthenticationResponseCode {
		checkNotRunningOnMainThread()
		checkEmptySession()
		return sessionService.authWithDeviceId()
	}

	fun signOut() {
		checkNotRunningOnMainThread()
		sessionService.logout()
		onSignOut.forEach { it.invoke() }
	}

	fun register(email: String, password: String, name: String): RegistrationResponseCode {
		checkNotRunningOnMainThread()
		return sessionService.register(email, password, name)
	}

	fun resetPassword(email: String): ResetPasswordResponseCode {
		checkNotRunningOnMainThread()
		return sessionService.resetPassword(email)
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
