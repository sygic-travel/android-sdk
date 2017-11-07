package com.sygic.travel.sdk.synchronization.di

import android.content.SharedPreferences
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.synchronization.facades.SynchronizationFacade
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.trips.api.TripConverter
import com.sygic.travel.sdk.trips.services.TripsService

internal val synchronizationModule = Kodein.Module {
	bind<SynchronizationFacade>() with singleton { SynchronizationFacade(instance<SynchronizationService>()) }
	bind<SynchronizationService>() with singleton {
		SynchronizationService(
			instance<SharedPreferences>(),
			instance<SygicTravelApiClient>(),
			instance<TripConverter>(),
			instance<TripsService>(),
			instance<FavoriteService>()
		)
	}
}
