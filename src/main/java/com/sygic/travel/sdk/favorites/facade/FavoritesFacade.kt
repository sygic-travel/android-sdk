package com.sygic.travel.sdk.favorites.facade

import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

/**
 * Favorites facade provides methods for managing users' favorite places.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class FavoritesFacade internal constructor(
	private val favoritesService: FavoriteService
) {
	fun addToFavorites(placeId: String) {
		checkNotRunningOnMainThread()
		return favoritesService.addPlace(placeId)
	}

	fun removeFromFavorites(placeId: String) {
		checkNotRunningOnMainThread()
		return favoritesService.softDeletePlace(placeId)
	}

	fun getFavoritePlaceIds(): List<String> {
		checkNotRunningOnMainThread()
		return favoritesService.getPlaces()
	}

	internal fun clearUserData() {
		favoritesService.clearUserData()
	}
}
