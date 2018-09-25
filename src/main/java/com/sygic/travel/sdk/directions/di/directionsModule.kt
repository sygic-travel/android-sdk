package com.sygic.travel.sdk.directions.di

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.directions.api.DirectionConverter
import com.sygic.travel.sdk.directions.facades.DirectionsFacade
import com.sygic.travel.sdk.directions.services.ApiDirectionsService
import org.koin.dsl.module.module

internal val directionsModule = module {
	single {
		ApiDirectionsService(
			get<SygicTravelApiClient>(),
			get<DirectionConverter>()
		)
	}
	single {
		DirectionsFacade(get<ApiDirectionsService>())
	}
	single {
		DirectionConverter()
	}
}
