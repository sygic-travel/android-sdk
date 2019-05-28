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
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

internal object DISetup {
	fun setup(applicationContext: Context, sdkConfig: SdkConfig): KoinApplication {
		val properties = mapOf(
			"userDataSupported" to (sdkConfig.clientId != null),
			"clientId" to (sdkConfig.clientId ?: ""),
			"apiKey" to sdkConfig.apiKey,
			"debugMode" to sdkConfig.debugMode,
			"sygicAuthUrl" to sdkConfig.sygicAuthUrl,
			"sygicTravelApiUrl" to sdkConfig.sygicTravelApiUrl,
			"httpCacheEnabled" to sdkConfig.httpCacheEnabled,
			"httpCacheSize" to sdkConfig.httpCacheSize,
			"defaultLanguage" to sdkConfig.language
		)

		return koinApplication {
			if (sdkConfig.debugMode) {
				androidLogger()
			}

			androidContext(applicationContext)

			modules(
				sessionModule,
				dbModule,
				directionsModule,
				eventsModule,
				favoritesModule,
				generalModule,
				placesModule,
				sygicAuthApiModule,
				sygicTravelApiModule,
				synchronizationModule,
				toursModule,
				tripsModule
			)

			properties(properties)
		}
	}
}
