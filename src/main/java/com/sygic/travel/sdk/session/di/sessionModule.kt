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
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val sessionModule = Kodein.Module("sessionModule") {
	bind<AuthStorageService>() with singleton {
		AuthStorageService(instance<SharedPreferences>())
	}

	bind<SessionService>() with singleton {
		SessionService(
			instance<SygicSsoApiClient>(),
			instance<AuthStorageService>(),
			instance<String>("clientId"),
			instance<Moshi>()
		)
	}

	bind<SessionFacade>() with singleton {
		checkUserDataSupport(instance<Boolean>("userDataSupported"), "Session")
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
