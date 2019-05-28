package com.sygic.travel.sdk.places.di

import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.places.facade.PlacesReviewsFacade
import com.sygic.travel.sdk.places.service.PlacesReviewsService
import com.sygic.travel.sdk.places.service.PlacesService
import org.koin.dsl.module

internal val placesModule = module {
	single {
		PlacesService(
			get<SygicTravelApiClient>(),
			get<Moshi>()
		)
	}
	single {
		PlacesFacade(get<PlacesService>())
	}
	single {
		PlacesReviewsService(get<SygicTravelApiClient>())
	}
	single {
		PlacesReviewsFacade(get<PlacesReviewsService>())
	}
}
