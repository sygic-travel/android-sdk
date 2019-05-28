package com.sygic.travel.sdk

import android.content.Context
import com.sygic.travel.sdk.common.Language
import com.sygic.travel.sdk.common.api.interceptors.LocaleInterceptor
import com.sygic.travel.sdk.common.di.DISetup
import com.sygic.travel.sdk.directions.facades.DirectionsFacade
import com.sygic.travel.sdk.events.facades.EventsFacade
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.places.facade.PlacesReviewsFacade
import com.sygic.travel.sdk.session.facade.SessionFacade
import com.sygic.travel.sdk.synchronization.facades.SynchronizationFacade
import com.sygic.travel.sdk.tours.facade.ToursFacade
import com.sygic.travel.sdk.trips.facades.TripsFacade
import org.koin.core.KoinApplication

/**
 * Provides public methods for requesting API.
 */
@Suppress("unused")
class Sdk(
	applicationContext: Context,
	private val sdkConfig: SdkConfig
) {
	private val koinApplication: KoinApplication = DISetup.setup(applicationContext, sdkConfig)

	var language: Language
		get() = sdkConfig.language
		set(value) {
			sdkConfig.language = value
			koinApplication.koin.get<LocaleInterceptor>().updateLanguage(value)
		}

	val directionsFacade: DirectionsFacade by lazy { koinApplication.koin.get<DirectionsFacade>() }
	val eventsFacade: EventsFacade by lazy { koinApplication.koin.get<EventsFacade>() }
	val favoritesFacade: FavoritesFacade by lazy { koinApplication.koin.get<FavoritesFacade>() }
	val placesFacade: PlacesFacade by lazy { koinApplication.koin.get<PlacesFacade>() }
	val placeReviewsFacade: PlacesReviewsFacade by lazy { koinApplication.koin.get<PlacesReviewsFacade>() }
	val sessionFacade: SessionFacade by lazy { koinApplication.koin.get<SessionFacade>() }
	val synchronizationFacade: SynchronizationFacade by lazy { koinApplication.koin.get<SynchronizationFacade>() }
	val toursFacade: ToursFacade by lazy { koinApplication.koin.get<ToursFacade>() }
	val tripsFacade: TripsFacade by lazy { koinApplication.koin.get<TripsFacade>() }
}
