package com.sygic.travel.sdk.favorites.facade

import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class FavoritesFacade(private val favoritesService: FavoriteService) {
	/**
	 * Stores a place's id in a local persistent storage. The place is added to the favorites.
	 * @param id A place's id, which is stored.
	 */
	fun addPlaceToFavorites(id: String) {
		checkNotRunningOnMainThread()
		return favoritesService.addPlaceToFavorites(id)
	}

	/**
	 * Removes a place's id from a local persistent storage. The place is removed from the favorites.
	 * @param id A place's id, which is removed.
	 */
	fun removePlaceFromFavorites(id: String) {
		checkNotRunningOnMainThread()
		return favoritesService.removePlaceFromFavorites(id)
	}

	/**
	 * Method returns a list of all favorite places' ids.
	 */
	fun getFavoritesIds(): List<String>? {
		checkNotRunningOnMainThread()
		return favoritesService.getFavoritesIds()
	}

	internal fun clearUserData() {
		favoritesService.clearUserData()
	}
}
