package com.sygic.travel.sdk

import android.content.Context
import com.github.salomonbrys.kodein.instance
import com.sygic.travel.sdk.common.Language
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor
import com.sygic.travel.sdk.common.di.KodeinSetup
import com.sygic.travel.sdk.directions.facades.DirectionsFacade
import com.sygic.travel.sdk.events.facades.EventsFacade
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.session.facade.SessionFacade
import com.sygic.travel.sdk.synchronization.facades.SynchronizationFacade
import com.sygic.travel.sdk.tours.facade.ToursFacade
import com.sygic.travel.sdk.trips.facades.TripsFacade

/**
 * Provides public methods for requesting API.
 */
@Suppress("unused")
class Sdk(
	applicationContext: Context,
	private val sdkConfig: SdkConfig
) {
	var language: Language
		get() = sdkConfig.language
		set(value) {
			sdkConfig.language = value
			kodein.instance<LocaleInterceptor>().updateLanguage(value)
		}

	val directionsFacade: DirectionsFacade by lazy {
		kodein.instance<DirectionsFacade>()
	}

	val eventsFacade: EventsFacade by lazy {
		kodein.instance<EventsFacade>()
	}

	val favoritesFacade: FavoritesFacade by lazy {
		checkUserDataSupport("Favorites")
		kodein.instance<FavoritesFacade>()
	}

	val placesFacade: PlacesFacade by lazy {
		kodein.instance<PlacesFacade>()
	}

	val sessionFacade: SessionFacade by lazy {
		checkUserDataSupport("Session")
		kodein.instance<SessionFacade>()
	}

	val synchronizationFacade: SynchronizationFacade by lazy {
		checkUserDataSupport("Synchronization")
		kodein.instance<SynchronizationFacade>()
	}

	val toursFacade: ToursFacade by lazy {
		kodein.instance<ToursFacade>()
	}

	val tripsFacade: TripsFacade by lazy {
		checkUserDataSupport("Trips")
		kodein.instance<TripsFacade>()
	}

	private val kodein = KodeinSetup.setupKodein(applicationContext, sdkConfig)

	private fun checkUserDataSupport(module: String) {
		if (!kodein.instance<Boolean>("userDataSupported")) {
			throw IllegalStateException("$module module can be used only with enabled user-data support. To enable it, configure Sdk with clientId.")
		}
	}
}
