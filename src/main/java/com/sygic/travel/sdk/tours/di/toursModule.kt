package com.sygic.travel.sdk.tours.di

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.tours.facade.ToursFacade
import com.sygic.travel.sdk.tours.service.ToursService
import org.koin.dsl.module.module

internal val toursModule = module {
	single {
		ToursService(get<SygicTravelApiClient>())
	}
	single {
		ToursFacade(get<ToursService>())
	}
}
