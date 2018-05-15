package com.sygic.travel.sdk.session.service

import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.session.api.SygicSsoApiClient
import com.sygic.travel.sdk.session.api.model.SessionResponse
import com.sygic.travel.sdk.testing.InMemorySharedPreferences
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test
import retrofit2.Response
import java.util.Date

class SessionServiceTest {
	@Test
	fun testRefreshTokenFail() {
		val sharedPreferences = InMemorySharedPreferences()
		val sygicSsoClient = mockk<SygicSsoApiClient>()
		val authStorageSsoApiClient = AuthStorageService(sharedPreferences)
		authStorageSsoApiClient.setUserSession(
			accessToken = "access_token",
			refreshToken = "refresh_token",
			secondsToExpiration = -60
		)
		val callResponseInner = mockk<Response<SessionResponse>>()
		val moshi = mockk<Moshi>()

		val service = SessionService(
			sygicSsoClient,
			authStorageSsoApiClient,
			"clientId",
			moshi
		)

		every {
			sygicSsoClient.authenticate(any()).execute()
		} returns callResponseInner

		every { callResponseInner.isSuccessful } returns false
		every { callResponseInner.code() } returns 401


		val session1 = service.getSession()
		assertNotNull(session1)
		assertSame("access_token", session1!!.accessToken)

		Thread.sleep(1000)
		val session2 = service.getSession()
		assertNull(session2)
	}
}
