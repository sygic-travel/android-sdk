package com.sygic.travel.sdk.favorites.service

import com.sygic.travel.sdk.favorites.model.Favorite
import com.sygic.travel.sdk.favorites.model.daos.FavoriteDao

class FavoriteService(
	private val favoriteDao: FavoriteDao
) {

	/**
	 * Stores a place's id in a local persistent storage. The place is added to the favorites.
	 * @param id A place's id, which is stored.
	 */
	fun addPlaceToFavorites(id: String) {
		val favorite = Favorite()
		favorite.id = id
		favoriteDao.insert(favorite)
	}

	/**
	 * Removes a place's id from a local persistent storage. The place is removed from the favorites.
	 * @param id A place's id, which is removed.
	 */
	fun removePlaceFromFavorites(id: String) {
		val favorite = Favorite()
		favorite.id = id
		favoriteDao.delete(favorite)
	}

	/**
	 * Method returns a list of all favorite places' ids.
	 */
	fun getFavoritesIds(): List<String> {
		val favorites = favoriteDao.loadAll()
		return favorites.map { it.id!! }
	}

	fun clearUserData() {
		favoriteDao.deleteAll()
	}
}
