package com.sygic.travel.sdk.session.di

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.session.api.SygicSsoApiClient
import com.sygic.travel.sdk.session.facade.SessionFacade
import com.sygic.travel.sdk.session.service.AuthStorageService
import com.sygic.travel.sdk.session.service.SessionService
import com.sygic.travel.sdk.synchronization.facades.SynchronizationFacade
import com.sygic.travel.sdk.trips.facades.TripsFacade
import com.sygic.travel.sdk.utils.checkUserDataSupport
import org.koin.dsl.module.module

internal val sessionModule = module {
	single {
		AuthStorageService(get<SharedPreferences>())
	}
	single {
		SessionService(
			get<SygicSsoApiClient>(),
			get<AuthStorageService>(),
			getProperty<String>("clientId"),
			get<Moshi>()
		)
	}
	single {
		checkUserDataSupport(getProperty("userDataSupported"), "Session")
		val authFacade = SessionFacade(
			get<SessionService>()
		)
		authFacade.onSignOut.add {
			get<TripsFacade>().clearUserData()
			get<FavoritesFacade>().clearUserData()
			get<SynchronizationFacade>().clearUserData()
		}
		authFacade
	}
}
