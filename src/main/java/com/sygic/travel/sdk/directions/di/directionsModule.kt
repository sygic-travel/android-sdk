package com.sygic.travel.sdk.directions.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.directions.facades.DirectionsFacade
import com.sygic.travel.sdk.directions.services.ApiDirectionsService
import com.sygic.travel.sdk.directions.services.CacheService
import com.sygic.travel.sdk.directions.services.DirectionsService
import com.sygic.travel.sdk.directions.services.NaiveDirectionsService

internal val directionsModule = Kodein.Module {
	bind<ApiDirectionsService>() with singleton {
		ApiDirectionsService(
			instance<SygicTravelApiClient>(),
			instance<NaiveDirectionsService>()
		)
	}

	bind<NaiveDirectionsService>() with singleton {
		NaiveDirectionsService()
	}

	bind<CacheService>() with singleton {
		CacheService(instance<Context>())
	}

	bind<DirectionsService>() with singleton {
		DirectionsService(
			instance<ApiDirectionsService>(),
			instance<NaiveDirectionsService>(),
			instance<CacheService>()
		)
	}

	bind<DirectionsFacade>() with singleton {
		DirectionsFacade(instance<DirectionsService>())
	}
}
