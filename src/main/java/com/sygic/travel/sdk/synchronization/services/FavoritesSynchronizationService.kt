package com.sygic.travel.sdk.synchronization.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.favorites.api.model.FavoriteRequest
import com.sygic.travel.sdk.favorites.model.Favorite
import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.synchronization.model.SynchronizationResult
import timber.log.Timber

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
			if (favorite.id.startsWith("*")) {
				Timber.e("Favorite ${favorite.id} cannot be synced because it has place with local id.")
				continue
			}
			if (favorite.state == Favorite.STATE_TO_ADD) {
				val createResponse = apiClient.createFavorite(FavoriteRequest(favorite.id)).execute()
				when {
					createResponse.isSuccessful -> {
						favoriteService.markAsSynchronized(favorite)
					}
					createResponse.code() in 400..499 -> {
						// place id does not exist
						favoriteService.hardDeletePlace(favorite.id)
					}
					else -> {
						// try deleting next time
					}
				}

			} else if (favorite.state == Favorite.STATE_TO_REMOVE) {
				val deleteResponse = apiClient.deleteFavorite(FavoriteRequest(favorite.id)).execute()
				if (deleteResponse.isSuccessful || deleteResponse.code() in 400..499) {
					favoriteService.hardDeletePlace(favorite.id)
				} else {
					// try deleting next time
				}
			}
		}

		syncResult.changedFavoriteIds.addAll(addedFavoriteIds.union(deletedFavoriteIds))
	}
}
