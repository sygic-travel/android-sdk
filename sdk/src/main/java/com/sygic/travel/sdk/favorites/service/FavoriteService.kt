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
		favorite.state = Favorite.STATE_TO_ADD
		favoriteDao.insert(favorite)
	}

	/**
	 * Removes a place's id from a local persistent storage. The place is removed from the favorites.
	 * @param id A place's id, which is removed.
	 */
	fun removePlaceFromFavorites(id: String) {
		val favorite = favoriteDao.get(id) ?: return
		favorite.state = Favorite.STATE_TO_REMOVE
		favoriteDao.insert(favorite)
	}

	/**
	 * Method returns a list of all favorite places' ids.
	 */
	fun getFavoritesIds(): List<String> {
		val favorites = favoriteDao.findAll()
		return favorites.map { it.id }
	}

	fun getFavoritesForSynchronization(): List<Favorite> {
		return favoriteDao.findForSynchronization()
	}

	fun markAsSynchronized(favorite: Favorite) {
		favorite.state = Favorite.STATE_SYNCED
		favoriteDao.update(favorite)
	}

	fun clearUserData() {
		favoriteDao.deleteAll()
	}
}
