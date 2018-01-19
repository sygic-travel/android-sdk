package com.sygic.travel.sdk.favorites.facade

import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class FavoritesFacade internal constructor(
	private val favoritesService: FavoriteService
) {
	fun addPlace(placeId: String) {
		checkNotRunningOnMainThread()
		return favoritesService.addPlace(placeId)
	}

	fun removePlace(placeId: String) {
		checkNotRunningOnMainThread()
		return favoritesService.removePlace(placeId)
	}

	fun getPlaceIds(): List<String> {
		checkNotRunningOnMainThread()
		return favoritesService.getPlaces()
	}

	internal fun clearUserData() {
		favoritesService.clearUserData()
	}
}
