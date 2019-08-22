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
import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.dsl.koinApplication
import timber.log.Timber

internal object DISetup {
	fun setup(applicationContext: Context, sdkConfig: SdkConfig): KoinApplication {
		val properties = mapOf(
			"userDataSupported" to (sdkConfig.clientId != null),
			"clientId" to (sdkConfig.clientId ?: ""),
			"apiKey" to sdkConfig.apiKey,
			"sygicAuthUrl" to sdkConfig.sygicAuthUrl,
			"sygicTravelApiUrl" to sdkConfig.sygicTravelApiUrl,
			"httpCacheEnabled" to sdkConfig.httpCacheEnabled,
			"httpCacheSize" to sdkConfig.httpCacheSize,
			"defaultLanguage" to sdkConfig.language
		)

		return koinApplication {
			logger(
				object : Logger() {
					override fun log(level: Level, msg: MESSAGE) {
						when (level) {
							Level.DEBUG -> Timber.d(msg)
							Level.INFO -> Timber.i(msg)
							Level.ERROR -> Timber.e(msg)
						}
					}
				}
			)

			androidContext(applicationContext)

			modules(listOf(
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
			))

			properties(properties)
		}
	}
}
