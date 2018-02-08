package com.sygic.travel.sdk.synchronization.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.favorites.api.model.FavoriteRequest
import com.sygic.travel.sdk.favorites.model.Favorite
import com.sygic.travel.sdk.favorites.service.FavoriteService

internal class FavoritesSynchronizationService constructor(
	private val apiClient: SygicTravelApiClient,
	private val favoriteService: FavoriteService
) {
	fun sync(addedFavoriteIds: List<String>, deletedFavoriteIds: List<String>): FavoritesSynchronizationResult {
		for (favoriteId in addedFavoriteIds) {
			favoriteService.addPlace(favoriteId)
		}

		for (favoriteId in deletedFavoriteIds) {
			favoriteService.removePlace(favoriteId)
		}

		for (favorite in favoriteService.getFavoritesForSynchronization()) {
			if (favorite.state == Favorite.STATE_TO_ADD) {
				val response = apiClient.createFavorite(FavoriteRequest(favorite.id)).execute()
				if (response.isSuccessful) {
					favoriteService.markAsSynchronized(favorite)
				}
			} else if (favorite.state == Favorite.STATE_TO_REMOVE) {
				val response = apiClient.deleteFavorite(FavoriteRequest(favorite.id)).execute()
				if (response.isSuccessful) {
					favoriteService.markAsSynchronized(favorite)
				}
			}
		}

		return FavoritesSynchronizationResult(
			changedFavoriteIds = addedFavoriteIds.union(deletedFavoriteIds)
		)
	}

	class FavoritesSynchronizationResult(
		val changedFavoriteIds: Set<String>
	)
}
