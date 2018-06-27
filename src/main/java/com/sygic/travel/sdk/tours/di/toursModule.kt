package com.sygic.travel.sdk.tours.di

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.tours.facade.ToursFacade
import com.sygic.travel.sdk.tours.service.ToursService
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val toursModule = Kodein.Module("toursModule") {
	bind<ToursService>() with singleton {
		ToursService(instance<SygicTravelApiClient>())
	}

	bind<ToursFacade>() with singleton { ToursFacade(instance<ToursService>()) }
}
