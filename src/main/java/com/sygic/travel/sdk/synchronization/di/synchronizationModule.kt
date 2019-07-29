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
import org.koin.dsl.module

internal val synchronizationModule = module {
	single {
		checkUserDataSupport(getProperty("userDataSupported"), "Synchronization")
		SynchronizationFacade(
			get<SynchronizationService>(),
			get<TripsService>(),
			get<FavoriteService>()
		)
	}
	single {
		SynchronizationService(
			get<SharedPreferences>(),
			get<SygicTravelApiClient>(),
			get<FavoritesSynchronizationService>(),
			get<TripsSynchronizationService>()
		)
	}
	single {
		TripsSynchronizationService(
			get<SygicTravelApiClient>(),
			get<TripConverter>(),
			get<TripsService>()
		)
	}
	single {
		FavoritesSynchronizationService(
			get<SygicTravelApiClient>(),
			get<FavoriteService>()
		)
	}
}
