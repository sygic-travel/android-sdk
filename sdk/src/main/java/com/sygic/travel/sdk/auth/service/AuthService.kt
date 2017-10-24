package com.sygic.travel.sdk.auth.service

import com.sygic.travel.sdk.auth.api.SygicTravelAuthApiClient
import com.sygic.travel.sdk.auth.model.AuthorizationRequest
import com.sygic.travel.sdk.auth.model.UserRegistrationRequest
import com.sygic.travel.sdk.auth.model.UserRegistrationResponse
import com.sygic.travel.sdk.auth.model.UserSession
import retrofit2.HttpException
import retrofit2.Response
import java.util.Date

class AuthService(
	private val sygicTravelAuthClient: SygicTravelAuthApiClient,
	private val authStorageService: AuthStorageService,
	private val clientId: String
) {

	fun authorize(authRequest: AuthorizationRequest): UserSession? {
		val response = sygicTravelAuthClient.authorize(authRequest).execute()
		return if (response.isSuccessful) {
			val userSession = response.body()!!
			setAuthorizationStorage(
				userSession.accessToken,
				userSession.expiresIn,
				userSession.refreshToken
			)
			userSession
		} else {
			throw HttpException(response)
		}
	}

	fun register(userRegistrationRequest: UserRegistrationRequest): UserRegistrationResponse? {
		var clientSession = authStorageService.getClientSession() ?: initClientSession()

		var response = registerUserRequest(clientSession, userRegistrationRequest)
		if (response.code() == 401) { // client session is expired, let's reinitialize it
			clientSession = initClientSession()
			response = registerUserRequest(clientSession, userRegistrationRequest)
		}

		return if (response.isSuccessful) {
			response.body()
		} else {
			throw HttpException(response)
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

	fun setAuthorizationStorage(accessToken: String?, expiresIn: Long, refreshToken: String?) {
		authStorageService.setUserSession(accessToken)
		authStorageService.setTokenRefreshTime(expiresIn)
		authStorageService.setRefreshToken(refreshToken)
	}

	internal fun passwordAuthRequest(username: String, password: String): AuthorizationRequest {
		return AuthorizationRequest(
			clientId = clientId,
			grantType = "password",
			username = username,
			password = password,
			devicePlatform = "android"
		)
	}

	internal fun googleAuthRequest(googleToken: String?): AuthorizationRequest {
		return AuthorizationRequest(
			clientId = clientId,
			grantType = "google",
			authorizationCode = googleToken,
			devicePlatform = "android"
		)
	}

	internal fun facebookAuthRequest(facebookToken: String?): AuthorizationRequest {
		return AuthorizationRequest(
			clientId = clientId,
			grantType = "facebook",
			accessToken = facebookToken,
			devicePlatform = "android"
		)
	}

	internal fun deviceIdAuthRequest(deviceId: String?): AuthorizationRequest {
		return AuthorizationRequest(
			clientId = clientId,
			grantType = "client_credentials",
			deviceCode = deviceId,
			devicePlatform = "android"
		)
	}

	internal fun jwtTokenAuthRequest(jwtToken: String): AuthorizationRequest {
		return AuthorizationRequest(clientId = clientId, grantType = "external", token = jwtToken)
	}

	internal fun userRegistrationRequest(
		email: String,
		password: String,
		name: String
	): UserRegistrationRequest {
		return UserRegistrationRequest(
			username = email,
			email = email,
			password = password,
			name = name,
			emailIsVerified = true
		)
	}

	private fun registerUserRequest(
		clientSession: String?,
		userRegistrationRequest: UserRegistrationRequest
	): Response<UserRegistrationResponse?> {
		return sygicTravelAuthClient.registerUser(
			"Bearer $clientSession",
			userRegistrationRequest
		).execute()
	}

	private fun refreshToken(refreshToken: String): UserSession? {
		val authRequest = AuthorizationRequest(
			clientId = clientId,
			grantType = "refresh_token",
			refreshToken = refreshToken
		)
		return authorize(authRequest)
	}

	private fun initClientSession(): String {
		val authRequest = AuthorizationRequest(
			clientId = clientId,
			grantType = "client_credentials"
		)
		val response = sygicTravelAuthClient.authorize(authRequest).execute()

		return if (response.isSuccessful) {
			val accessToken = response.body()!!.accessToken
			authStorageService.setClientSession(accessToken)
			accessToken
		} else throw HttpException(response)
	}
}
