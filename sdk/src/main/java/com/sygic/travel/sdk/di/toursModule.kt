package com.sygic.travel.sdk.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.places.api.SygicTravelApiClient
import com.sygic.travel.sdk.tours.facade.ToursFacade
import com.sygic.travel.sdk.tours.service.ToursService


internal val toursModule = Kodein.Module {
	bind<ToursService>() with singleton {
		ToursService(instance<SygicTravelApiClient>())
	}

	bind<ToursFacade>() with singleton { ToursFacade(instance<ToursService>()) }
}
