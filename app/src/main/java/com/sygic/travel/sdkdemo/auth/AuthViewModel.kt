package com.sygic.travel.sdkdemo.auth

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.widget.Toast
import com.sygic.travel.sdk.auth.AuthenticationResponseCode
import com.sygic.travel.sdk.auth.RegistrationResponseCode
import com.sygic.travel.sdk.auth.ResetPasswordResponseCode
import com.sygic.travel.sdkdemo.Application
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

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
		Single.fromCallable {
			val sdk = (activity.application as Application).sdk
			sdk.authFacade.registerUser(
				name = name,
				email = email,
				password = password
			)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ result ->
					when (result!!) {
						RegistrationResponseCode.OK -> {
							val authActivityIntent = Intent(activity, AuthActivity::class.java)
							activity.startActivity(authActivityIntent)
							Toast.makeText(
								activity,
								"You have been successfully signed up, please Sign In.",
								Toast.LENGTH_LONG
							).show()
						}
						RegistrationResponseCode.ERROR_ALREADY_REGISTERED -> {
							Toast.makeText(
								activity,
								"Email already registered",
								Toast.LENGTH_LONG
							).show()
						}
						RegistrationResponseCode.ERROR_EMAIL_INVALID_FORMAT -> {
							Toast.makeText(
								activity,
								"E-mail format is invalid.",
								Toast.LENGTH_LONG
							).show()
						}
						RegistrationResponseCode.ERROR_PASSWORD_MIN_LENGTH -> {
							Toast.makeText(
								activity,
								"Password is too short, minimum length is 6 characters.",
								Toast.LENGTH_LONG
							).show()
						}
						RegistrationResponseCode.ERROR -> {
							Toast.makeText(
								activity,
								"Sign Up Failed :(",
								Toast.LENGTH_LONG
							).show()
						}
					}
				},
				{ error ->
					Toast.makeText(
						activity,
						"Sign Up Failed :(",
						Toast.LENGTH_LONG
					).show()
					error.printStackTrace()
				}
			)
	}

	fun signInPassword(activity: AuthActivity, name: String, password: String) {
		Single.fromCallable {
			val sdk = (activity.application as Application).sdk
			sdk.authFacade.loginUserWithPassword(
				username = name,
				password = password
			)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(signInUiCallbackSuccess(activity), signInUiCallbackError(activity))
	}

	fun resetPassword(activity: AuthActivity, userName: String) {
		val sdk = (activity.application as Application).sdk
		Single.fromCallable {
			sdk.authFacade.resetPassword(
				email = userName
			)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ data ->
					when (data!!) {
						ResetPasswordResponseCode.OK -> {
							Toast.makeText(
								activity,
								"Reset password email was successfully sent.",
								Toast.LENGTH_LONG
							).show()
						}
						ResetPasswordResponseCode.ERROR_USER_NOT_FOUND -> {
							Toast.makeText(
								activity,
								"Account was not found.",
								Toast.LENGTH_LONG
							).show()
						}
						ResetPasswordResponseCode.ERROR_EMAIL_INVALID_FORMAT -> {
							Toast.makeText(
								activity,
								"E-mail has invalid format.",
								Toast.LENGTH_LONG
							).show()
						}
						ResetPasswordResponseCode.ERROR -> {
							Toast.makeText(
								activity,
								"Reset password unknown error.",
								Toast.LENGTH_LONG
							).show()
						}
					}
				},
				{ exception ->
					Toast.makeText(
						activity,
						"Reset password network error.",
						Toast.LENGTH_LONG
					).show()
				}
			)
	}

	fun signInGoogle(activity: AuthActivity, accessToken: String) {
		val sdk = (activity.application as Application).sdk
		Single.fromCallable {
			sdk.authFacade.loginUserWithGoogle(
				googleToken = accessToken
			)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(signInUiCallbackSuccess(activity), signInUiCallbackError(activity))
	}

	fun signInFacebook(activity: AuthActivity, accessToken: String) {
		val sdk = (activity.application as Application).sdk
		Single.fromCallable {
			sdk.authFacade.loginUserWithFacebook(
				facebookToken = accessToken
			)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(signInUiCallbackSuccess(activity), signInUiCallbackError(activity))
	}

	fun anonymousSignIn(activity: AuthActivity) {
		val sdk = (activity.application as Application).sdk
		Single.fromCallable {
			sdk.authFacade.loginUserWithDeviceId()
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(signInUiCallbackSuccess(activity), signInUiCallbackError(activity))
	}

	fun jwtTokenSignIn(activity: AuthActivity) {
		val sdk = (activity.application as Application).sdk
		Single.fromCallable {
			sdk.authFacade.loginUserWithJwtToken(
				// JWT Token must include 'external_user id' parameter in payload and has to be
				// signed by 'client_secret'. Token should be generated on the server side.
				jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
					"eyJleHRlcm5hbF91c2VyX2lkIjoiMTIzNDU2Nzg5MCJ9." +
					"2uOKdvIQ6nKG8FIbrSuhc3vBF_t4kwGyhVT0RDJ-UVQ"
			)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(signInUiCallbackSuccess(activity), signInUiCallbackError(activity))
	}

	fun logout(activity: AuthActivity) {
		val sdk = (activity.application as Application).sdk
		sdk.authFacade.logoutUser()
		refreshLoginStatus(activity)
	}

	private fun signInUiCallbackSuccess(activity: AuthActivity): Consumer<AuthenticationResponseCode> {
		return Consumer { data ->
			when (data!!) {
				AuthenticationResponseCode.OK -> {
					refreshLoginStatus(activity)
					Toast.makeText(
						activity,
						"You have been successfully signed in.",
						Toast.LENGTH_LONG
					).show()
				}
				AuthenticationResponseCode.ERROR_INVALID_CREDENTIALS -> {
					Toast.makeText(
						activity,
						"Invalid credentials.",
						Toast.LENGTH_LONG
					).show()
				}
				AuthenticationResponseCode.ERROR -> {
					Toast.makeText(
						activity,
						"Sign In failed :(",
						Toast.LENGTH_LONG
					).show()
				}
			}
		}
	}

	private fun signInUiCallbackError(activity: AuthActivity): Consumer<Throwable> {
		return Consumer { exception ->
			Toast.makeText(
				activity,
				"Sign In failed :(",
				Toast.LENGTH_LONG
			).show()
			exception.printStackTrace()
		}
	}
}
