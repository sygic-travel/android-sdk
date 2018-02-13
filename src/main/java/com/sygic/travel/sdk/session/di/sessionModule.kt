package com.sygic.travel.sdk.session.di

import android.content.SharedPreferences
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.gson.Gson
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.session.api.SygicSsoApiClient
import com.sygic.travel.sdk.session.facade.SessionFacade
import com.sygic.travel.sdk.session.service.AuthStorageService
import com.sygic.travel.sdk.session.service.SessionService
import com.sygic.travel.sdk.synchronization.facades.SynchronizationFacade
import com.sygic.travel.sdk.trips.facades.TripsFacade

internal val sessionModule = Kodein.Module {
	bind<AuthStorageService>() with singleton {
		AuthStorageService(instance<SharedPreferences>())
	}

	bind<SessionService>() with singleton {
		SessionService(
			instance<SygicSsoApiClient>(),
			instance<AuthStorageService>(),
			instance<String>("clientId"),
			instance<Gson>("sygicAuthGson")
		)
	}

	bind<SessionFacade>() with singleton {
		val authFacade = SessionFacade(
			instance<SessionService>()
		)
		authFacade.onSignOut.add {
			instance<TripsFacade>().clearUserData()
			instance<FavoritesFacade>().clearUserData()
			instance<SynchronizationFacade>().clearUserData()
		}
		authFacade
	}
}
