package com.sygic.travel.sdk

import android.content.Context
import com.sygic.travel.sdk.common.Language
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor
import com.sygic.travel.sdk.common.di.KodeinSetup
import com.sygic.travel.sdk.directions.facades.DirectionsFacade
import com.sygic.travel.sdk.events.facades.EventsFacade
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.places.facade.PlacesReviewsFacade
import com.sygic.travel.sdk.session.facade.SessionFacade
import com.sygic.travel.sdk.synchronization.facades.SynchronizationFacade
import com.sygic.travel.sdk.tours.facade.ToursFacade
import com.sygic.travel.sdk.trips.facades.TripsFacade
import org.kodein.di.erased.instance

/**
 * Provides public methods for requesting API.
 */
@Suppress("unused")
class Sdk(
	applicationContext: Context,
	private val sdkConfig: SdkConfig
) {
	private val kodein = KodeinSetup.setupKodein(applicationContext, sdkConfig)

	var language: Language
		get() = sdkConfig.language
		set(value) {
			sdkConfig.language = value
			val localeInterceptor by kodein.instance<LocaleInterceptor>()
			localeInterceptor.updateLanguage(value)
		}

	val directionsFacade: DirectionsFacade by kodein.instance()
	val eventsFacade: EventsFacade by kodein.instance()
	val favoritesFacade: FavoritesFacade by kodein.instance()
	val placesFacade: PlacesFacade by kodein.instance()
	val placeReviewsFacade: PlacesReviewsFacade by kodein.instance()
	val sessionFacade: SessionFacade by kodein.instance()
	val synchronizationFacade: SynchronizationFacade by kodein.instance()
	val toursFacade: ToursFacade by kodein.instance()
	val tripsFacade: TripsFacade by kodein.instance()
}
