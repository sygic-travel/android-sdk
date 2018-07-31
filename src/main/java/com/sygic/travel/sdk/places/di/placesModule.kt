package com.sygic.travel.sdk.places.di

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.places.service.PlacesService
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val placesModule = Kodein.Module("placesModule") {
	bind<PlacesService>() with singleton {
		PlacesService(instance<SygicTravelApiClient>())
	}

	bind<PlacesFacade>() with singleton { PlacesFacade(instance<PlacesService>()) }
}
