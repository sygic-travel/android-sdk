package com.sygic.travel.sdkdemo.auth

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.widget.Toast
import com.sygic.travel.sdk.auth.model.UserRegistrationResponse
import com.sygic.travel.sdk.auth.model.UserSession
import com.sygic.travel.sdkdemo.Application
import com.sygic.travel.sdkdemo.utils.UiCallback

class AuthViewModel : ViewModel() {

	fun refreshLoginStatus(activity: AuthActivity) {
		val sdk = (activity.application as Application).sdk
		val userSession = sdk.authFacade.getUserSession()
		if (userSession?.accessToken != null) {
			activity.setLoggedIn()
		} else {
			activity.setLoggedOut()
		}
	}

	fun signUpUser(activity: SignUpActivity, name: String, email: String, password: String) {
		val sdk = (activity.application as Application).sdk
		sdk.authFacade.registerUser(
			name = name,
			email = email,
			password = password,
			callback = (object : UiCallback<UserRegistrationResponse?>(activity) {
				override fun onUiSuccess(data: UserRegistrationResponse?) {
					val authActivityIntent = Intent(activity, AuthActivity::class.java)
					activity.startActivity(authActivityIntent)
					Toast.makeText(
						activity,
						"You have been successfully signed up, please Sign In.",
						Toast.LENGTH_LONG
					).show()
				}

				override fun onUiFailure(exception: Throwable) {
					Toast.makeText(
						activity,
						"Sign Up Failed :(",
						Toast.LENGTH_LONG
					).show()
					exception.printStackTrace()
				}
			})
		)
	}

	fun signInPassword(activity: AuthActivity, name: String, password: String) {
		val sdk = (activity.application as Application).sdk
		sdk.authFacade.loginUserWithPassword(
			username = name,
			password = password,
			callback = signInUiCallback(activity)
		)
	}

	fun signInGoogle(activity: AuthActivity, accessToken: String?) {
		val sdk = (activity.application as Application).sdk
		sdk.authFacade.loginUserWithGoogle(
			googleToken = accessToken,
			callback = signInUiCallback(activity)
		)
	}

	fun signInFacebook(activity: AuthActivity, accessToken: String?) {
		val sdk = (activity.application as Application).sdk
		sdk.authFacade.loginUserWithFacebook(
			facebookToken = accessToken,
			callback = signInUiCallback(activity)
		)
	}

	fun anonymousSignIn(activity: AuthActivity) {
		val sdk = (activity.application as Application).sdk
		sdk.authFacade.loginUserWithDeviceId(signInUiCallback(activity))
	}

	fun jwtTokenSignIn(activity: AuthActivity) {
		val sdk = (activity.application as Application).sdk
		sdk.authFacade.loginUserWithJwtToken(
			// JWT Token must include 'external_user id' parameter in payload and has to be
			// signed by 'client_secret'. Token should be generated on the server side.
			jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
				"eyJleHRlcm5hbF91c2VyX2lkIjoiMTIzNDU2Nzg5MCJ9." +
				"2uOKdvIQ6nKG8FIbrSuhc3vBF_t4kwGyhVT0RDJ-UVQ",
			callback = signInUiCallback(activity)
		)
	}

	fun logout(activity: AuthActivity) {
		val sdk = (activity.application as Application).sdk
		sdk.authFacade.logoutUser()
		refreshLoginStatus(activity)
	}

	private fun signInUiCallback(activity: AuthActivity): UiCallback<UserSession?> {
		return (object : UiCallback<UserSession?>(activity) {
			override fun onUiSuccess(data: UserSession?) {
				refreshLoginStatus(activity)
				Toast.makeText(
					activity,
					"You have been successfully signed in.",
					Toast.LENGTH_LONG
				).show()
			}

			override fun onUiFailure(exception: Throwable) {
				Toast.makeText(
					activity,
					"Sign In failed :(",
					Toast.LENGTH_LONG
				).show()
				exception.printStackTrace()
			}
		})
	}
}
