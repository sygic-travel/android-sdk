package com.sygic.travel.sdk.directions.di

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.directions.facades.DirectionsFacade
import com.sygic.travel.sdk.directions.services.ApiDirectionsService
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val directionsModule = Kodein.Module("directionsModule") {
	bind<ApiDirectionsService>() with singleton {
		ApiDirectionsService(
			instance<SygicTravelApiClient>()
		)
	}

	bind<DirectionsFacade>() with singleton {
		DirectionsFacade(instance<ApiDirectionsService>())
	}
}
