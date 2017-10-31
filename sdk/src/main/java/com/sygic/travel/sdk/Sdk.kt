package com.sygic.travel.sdk

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.sygic.travel.sdk.auth.facade.AuthFacade
import com.sygic.travel.sdk.di.KodeinSetup
import com.sygic.travel.sdk.directions.facades.DirectionsFacade
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.tours.facade.ToursFacade

/**
 * Provides public methods for requesting API.
 */
class Sdk(
	clientId: String,
	xApiKey: String,
	context: Context,
	urlConfig: UrlConfig = object : UrlConfig {}
) {
	private var kodein: Kodein = KodeinSetup.setupKodein(clientId, xApiKey, context, urlConfig)

	val authFacade: AuthFacade
		get() {
			return kodein.instance()
		}

	val directionsFacade: DirectionsFacade
		get() {
			return kodein.instance()
		}

	val favoritesFacade: FavoritesFacade
		get() {
			return kodein.instance()
		}

	val placesFacade: PlacesFacade
		get() {
			return kodein.instance()
		}

	val toursFacade: ToursFacade
		get() {
			return kodein.instance()
		}
}
