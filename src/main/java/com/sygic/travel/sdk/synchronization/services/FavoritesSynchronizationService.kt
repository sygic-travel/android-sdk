package com.sygic.travel.sdk.synchronization.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.favorites.api.model.FavoriteRequest
import com.sygic.travel.sdk.favorites.model.Favorite
import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.synchronization.model.SynchronizationResult

internal class FavoritesSynchronizationService constructor(
	private val apiClient: SygicTravelApiClient,
	private val favoriteService: FavoriteService
) {
	fun sync(addedFavoriteIds: List<String>, deletedFavoriteIds: List<String>, syncResult: SynchronizationResult) {
		for (favoriteId in addedFavoriteIds) {
			favoriteService.addPlace(favoriteId)
		}

		for (favoriteId in deletedFavoriteIds) {
			favoriteService.hardDeletePlace(favoriteId)
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
					favoriteService.hardDeletePlace(favorite.id)
				}
			}
		}

		syncResult.changedFavoriteIds.addAll(addedFavoriteIds.union(deletedFavoriteIds))
	}
}
