package com.sygic.travel.sdk.favorites.facade

import com.sygic.travel.sdk.Callback
import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.utils.runAsync
import com.sygic.travel.sdk.utils.runWithCallback

class FavoritesFacade(private val favoritesService: FavoriteService) {

	/**
	 * Stores a place's id in a local persistent storage. The place is added to the favorites.
	 * @param id A place's id, which is stored.
	 */
	fun addPlaceToFavorites(id: String, callback: Callback<Unit>) {
		runWithCallback({ favoritesService.addPlaceToFavorites(id) }, callback)
	}

	/**
	 * Stores a place's id in a local persistent storage. The place is added to the favorites.
	 * @param id A place's id, which is stored.
	 */
	suspend fun addPlaceToFavorites(id: String) {
		return runAsync { favoritesService.addPlaceToFavorites(id) }
	}

	/**
	 * Removes a place's id from a local persistent storage. The place is removed from the favorites.
	 * @param id A place's id, which is removed.
	 */
	fun removePlaceFromFavorites(id: String, callback: Callback<Unit>) {
		runWithCallback({ favoritesService.removePlaceFromFavorites(id) }, callback)
	}

	/**
	 * Removes a place's id from a local persistent storage. The place is removed from the favorites.
	 * @param id A place's id, which is removed.
	 */
	suspend fun removePlaceFromFavorites(id: String) {
		return runAsync { favoritesService.removePlaceFromFavorites(id) }
	}

	/**
	 * Method returns a list of all favorite places' ids.
	 */
	fun getFavoritesIds(callback: Callback<List<String>?>) {
		runWithCallback({ favoritesService.getFavoritesIds() }, callback)
	}

	/**
	 * Method returns a list of all favorite places' ids.
	 */
	suspend fun getFavoritesIds(): List<String>? {
		return runAsync { favoritesService.getFavoritesIds() }
	}

	internal fun clearUserData() {
		favoritesService.clearUserData()
	}
}
