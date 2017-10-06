package com.sygic.travel.sdk

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.sygic.travel.sdk.di.KodeinSetup
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.places.facade.PlacesFacade
import com.sygic.travel.sdk.tours.facade.ToursFacade

/**
 * Provides public methods for requesting API.
 */
class Sdk(xApiKey: String, context: Context) {
	private var kodein: Kodein = KodeinSetup.setupKodein(context, xApiKey)

	val placesFacade: PlacesFacade
		get() {
			return kodein.instance()
		}

	val toursFacade: ToursFacade
		get() {
			return kodein.instance()
		}

	val favoritesFacade: FavoritesFacade
		get() {
			return kodein.instance()
		}
}
