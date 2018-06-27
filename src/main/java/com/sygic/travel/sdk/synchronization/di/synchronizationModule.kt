package com.sygic.travel.sdk.synchronization.di

import android.content.SharedPreferences
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.synchronization.facades.SynchronizationFacade
import com.sygic.travel.sdk.synchronization.services.FavoritesSynchronizationService
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.synchronization.services.TripsSynchronizationService
import com.sygic.travel.sdk.trips.api.TripConverter
import com.sygic.travel.sdk.trips.services.TripsService
import com.sygic.travel.sdk.utils.checkUserDataSupport
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val synchronizationModule = Kodein.Module("synchronizationModule") {
	bind<SynchronizationFacade>() with singleton {
		checkUserDataSupport(instance<Boolean>("userDataSupported"), "Synchronization")
		SynchronizationFacade(
			instance<SynchronizationService>(),
			instance<TripsService>(),
			instance<FavoriteService>()
		)
	}
	bind<SynchronizationService>() with singleton {
		SynchronizationService(
			instance<SharedPreferences>(),
			instance<SygicTravelApiClient>(),
			instance<FavoritesSynchronizationService>(),
			instance<TripsSynchronizationService>()
		)
	}
	bind<TripsSynchronizationService>() with singleton {
		TripsSynchronizationService(
			instance<SygicTravelApiClient>(),
			instance<TripConverter>(),
			instance<TripsService>()
		)
	}
	bind<FavoritesSynchronizationService>() with singleton {
		FavoritesSynchronizationService(
			instance<SygicTravelApiClient>(),
			instance<FavoriteService>()
		)
	}
}
