package com.sygic.travel.sdk.auth.service

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sygic.travel.sdk.auth.AuthenticationResponseCode
import com.sygic.travel.sdk.auth.RegistrationResponseCode
import com.sygic.travel.sdk.auth.ResetPasswordResponseCode
import com.sygic.travel.sdk.auth.api.SygicAuthApiClient
import com.sygic.travel.sdk.auth.api.model.ResetPasswordRequest
import com.sygic.travel.sdk.auth.model.AuthenticationRequest
import com.sygic.travel.sdk.auth.model.UserRegistrationRequest
import com.sygic.travel.sdk.auth.model.UserSession
import retrofit2.HttpException
import java.util.Date

class AuthService(
	private val sygicAuthClient: SygicAuthApiClient,
	private val authStorageService: AuthStorageService,
	private val clientId: String,
	private val gson: Gson
) {
	companion object {
		private const val DEVICE_PLATFORM = "android"
	}

	fun authWithPassword(username: String, password: String): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			clientId = clientId,
			grantType = "password",
			username = username,
			password = password,
			deviceCode = deviceId,
			devicePlatform = DEVICE_PLATFORM
		))
	}

	fun authWithGoogleToken(token: String): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			clientId = clientId,
			grantType = "google",
			accessToken = token,
			deviceCode = deviceId,
			devicePlatform = DEVICE_PLATFORM
		))
	}

	fun authWithFacebookToken(token: String): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			clientId = clientId,
			grantType = "facebook",
			accessToken = token,
			deviceCode = deviceId,
			devicePlatform = DEVICE_PLATFORM
		))
	}

	fun authWithJwtToken(token: String): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			clientId = clientId,
			grantType = "external",
			token = token,
			deviceCode = deviceId,
			devicePlatform = DEVICE_PLATFORM
		))
	}

	fun authWithDeviceId(): AuthenticationResponseCode {
		val deviceId = authStorageService.getDeviceId()
		return authenticate(AuthenticationRequest(
			clientId = clientId,
			grantType = "client_credentials",
			deviceCode = deviceId,
			devicePlatform = DEVICE_PLATFORM
		))
	}

	fun register(name: String, email: String, password: String): RegistrationResponseCode {
		val userRegistrationRequest = UserRegistrationRequest(
			username = email,
			email = email,
			password = password,
			name = name,
			emailIsVerified = true
		)
		var clientSession = authStorageService.getClientSession() ?: initClientSession()

		var response = sygicAuthClient.registerUser("Bearer $clientSession", userRegistrationRequest).execute()
		if (response.code() == 401) { // client session is expired, let's reinitialize it
			clientSession = initClientSession()
			response = sygicAuthClient.registerUser("Bearer $clientSession", userRegistrationRequest).execute()
		}

		if (response.isSuccessful) {
			return RegistrationResponseCode.OK

		} else if (response.code() == 409) {
			return RegistrationResponseCode.ERROR_ALREADY_REGISTERED

		} else {
			val responseJson = gson.fromJson<JsonObject?>(response.errorBody()?.string(), JsonObject::class.java)
			return when (responseJson?.get("type")?.asString) {
				"validation.password.min_length" -> RegistrationResponseCode.ERROR_PASSWORD_MIN_LENGTH
				"validation.username.min_length", "validation.email.invalid_format" -> RegistrationResponseCode.ERROR_EMAIL_INVALID_FORMAT
				else -> RegistrationResponseCode.ERROR
			}
		}
	}

	fun resetPassword(email: String): ResetPasswordResponseCode {
		val resetPasswordRequest = ResetPasswordRequest(email)
		var clientSession = authStorageService.getClientSession() ?: initClientSession()

		var response = sygicAuthClient.resetPassword("Bearer $clientSession", resetPasswordRequest).execute()
		if (response.code() == 401) {
			clientSession = initClientSession()
			response = sygicAuthClient.resetPassword("Bearer $clientSession", resetPasswordRequest).execute()
		}

		return when {
			response.isSuccessful -> ResetPasswordResponseCode.OK
			response.code() == 404 -> ResetPasswordResponseCode.ERROR_USER_NOT_FOUND
			response.code() == 422 -> ResetPasswordResponseCode.ERROR_EMAIL_INVALID_FORMAT
			else -> ResetPasswordResponseCode.ERROR
		}
	}

	fun getUserSession(): UserSession? {
		val accessToken = authStorageService.getUserSession() ?: return null
		val refreshToken = authStorageService.getRefreshToken() ?: return null
		val refreshTimeExpiration = authStorageService.getSuggestedRefreshTime()

		if (Date().time >= refreshTimeExpiration) {
			refreshToken(refreshToken)
		}

		return UserSession(
			accessToken = accessToken,
			refreshToken = refreshToken,
			expiresIn = refreshTimeExpiration
		)
	}

	fun logout() {
		authStorageService.setUserSession(null)
		authStorageService.setTokenRefreshTime(0)
		authStorageService.setRefreshToken(null)
	}

	private fun authenticate(authRequest: AuthenticationRequest): AuthenticationResponseCode {
		val response = sygicAuthClient.authenticate(authRequest).execute()
		if (response.isSuccessful) {
			val userSession = response.body()!!
			authStorageService.setUserSession(userSession.accessToken)
			authStorageService.setTokenRefreshTime(userSession.expiresIn)
			authStorageService.setRefreshToken(userSession.refreshToken)
			return AuthenticationResponseCode.OK

		} else if (response.code() == 401) {
			return AuthenticationResponseCode.ERROR_INVALID_CREDENTIALS

		} else {
			return AuthenticationResponseCode.ERROR
		}
	}

	private fun refreshToken(refreshToken: String) {
		authenticate(AuthenticationRequest(
			clientId = clientId,
			grantType = "refresh_token",
			refreshToken = refreshToken
		))
	}

	private fun initClientSession(): String {
		val request = sygicAuthClient.authenticate(AuthenticationRequest(
			clientId = clientId,
			grantType = "client_credentials"
		))
		val response = request.execute()
		return if (response.isSuccessful) {
			val accessToken = response.body()!!.accessToken
			authStorageService.setClientSession(accessToken)
			accessToken
		} else throw HttpException(response)
	}
}
