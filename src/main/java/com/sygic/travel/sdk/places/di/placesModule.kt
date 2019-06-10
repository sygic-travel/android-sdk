package com.sygic.travel.sdk.places.di

import com.squareup.moshi.Moshi
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.database.Database
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.places.facade.PlacesReviewsFacade
import com.sygic.travel.sdk.places.model.daos.PlacesDao
import com.sygic.travel.sdk.places.service.PlacesReviewsService
import com.sygic.travel.sdk.places.service.PlacesService
import com.sygic.travel.sdk.utils.checkUserDataSupport
import org.koin.dsl.module

internal val placesModule = module {
	single {
		PlacesService(
			get<SygicTravelApiClient>(),
			get<Moshi>()
		)
	}
	single {
		checkUserDataSupport(getProperty("userDataSupported"), "Places.replaceLocalPlaceId")
		get<Database>().placesDao()
	}
	single {
		PlacesFacade(
			placesService = get<PlacesService>(),
			placesDao = { get<PlacesDao>() }
		)
	}
	single {
		PlacesReviewsService(get<SygicTravelApiClient>())
	}
	single {
		PlacesReviewsFacade(get<PlacesReviewsService>())
	}
}
