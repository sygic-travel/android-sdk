package com.sygic.travel.sdk.common.di

import android.content.Context
import com.sygic.travel.sdk.SdkConfig
import com.sygic.travel.sdk.common.database.di.dbModule
import com.sygic.travel.sdk.directions.di.directionsModule
import com.sygic.travel.sdk.events.di.eventsModule
import com.sygic.travel.sdk.favorites.di.favoritesModule
import com.sygic.travel.sdk.places.di.placesModule
import com.sygic.travel.sdk.session.di.sessionModule
import com.sygic.travel.sdk.synchronization.di.synchronizationModule
import com.sygic.travel.sdk.tours.di.toursModule
import com.sygic.travel.sdk.trips.di.tripsModule
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import org.kodein.di.erased.with

internal object KodeinSetup {
	fun setupKodein(
		applicationContext: Context,
		sdkConfig: SdkConfig
	) = Kodein {
		constant("userDataSupported") with (sdkConfig.clientId != null)
		constant("clientId") with (sdkConfig.clientId ?: "")
		constant("apiKey") with sdkConfig.apiKey
		constant("debugMode") with sdkConfig.debugMode
		constant("sygicAuthUrl") with sdkConfig.sygicAuthUrl
		constant("sygicTravelApiUrl") with sdkConfig.sygicTravelApiUrl
		constant("httpCacheEnabled") with sdkConfig.httpCacheEnabled
		constant("httpCacheSize") with sdkConfig.httpCacheSize
		constant("defaultLanguage") with sdkConfig.language

		bind<Context>() with singleton { applicationContext }

		import(sessionModule)
		import(dbModule)
		import(directionsModule)
		import(eventsModule)
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
