package com.sygic.travel.sdk.common.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import com.github.salomonbrys.kodein.with
import com.sygic.travel.sdk.SdkConfig
import com.sygic.travel.sdk.auth.di.authModule
import com.sygic.travel.sdk.common.database.di.dbModule
import com.sygic.travel.sdk.directions.di.directionsModule
import com.sygic.travel.sdk.favorites.di.favoritesModule
import com.sygic.travel.sdk.places.di.placesModule
import com.sygic.travel.sdk.synchronization.di.synchronizationModule
import com.sygic.travel.sdk.tours.di.toursModule
import com.sygic.travel.sdk.trips.di.tripsModule

internal object KodeinSetup {
	fun setupKodein(
		applicationContext: Context,
		sdkConfig: SdkConfig
	) = Kodein {
		constant("clientId") with sdkConfig.clientId
		constant("apiKey") with sdkConfig.apiKey
		constant("debugMode") with sdkConfig.debugMode
		constant("sygicAuthUrl") with sdkConfig.sygicAuthUrl
		constant("sygicTravelApiUrl") with sdkConfig.sygicTravelApiUrl

		bind<Context>() with singleton { applicationContext }

		import(authModule)
		import(dbModule)
		import(directionsModule)
		import(favoritesModule)
		import(generalModule)
		import(placesModule)
		import(sygicAuthApiModule)
		import(sygicTravelApiModule)
		import(synchronizationModule)
		import(toursModule)
		import(tripsModule)
	}
}
