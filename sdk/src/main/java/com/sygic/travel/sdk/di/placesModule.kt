package com.sygic.travel.sdk.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.places.api.SygicTravelApiClient
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.places.service.PlacesService

internal val placesModule = Kodein.Module {
	bind<PlacesService>() with singleton {
		PlacesService(instance<SygicTravelApiClient>())
	}

	bind<PlacesFacade>() with singleton { PlacesFacade(instance<PlacesService>()) }
}
