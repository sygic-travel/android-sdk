package com.sygic.travel.sdk.directions.di

import android.content.Context
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.directions.facades.DirectionsFacade
import com.sygic.travel.sdk.directions.services.ApiDirectionsService
import com.sygic.travel.sdk.directions.services.CacheService
import com.sygic.travel.sdk.directions.services.DirectionsService
import com.sygic.travel.sdk.directions.services.EstimatedDirectionsService
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

	bind<EstimatedDirectionsService>() with singleton {
		EstimatedDirectionsService()
	}

	bind<CacheService>() with singleton {
		CacheService(instance<Context>())
	}

	bind<DirectionsService>() with singleton {
		DirectionsService(
			instance<ApiDirectionsService>(),
			instance<EstimatedDirectionsService>(),
			instance<CacheService>()
		)
	}

	bind<DirectionsFacade>() with singleton {
		DirectionsFacade(instance<DirectionsService>())
	}
}
