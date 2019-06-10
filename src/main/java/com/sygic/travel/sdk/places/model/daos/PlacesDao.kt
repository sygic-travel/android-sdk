package com.sygic.travel.sdk.places.model.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class PlacesDao {
	@Transaction
	open fun replacePlaceId(oldPlaceId: String, newPlaceId: String) {
		replacePlaceIdInTrips(oldPlaceId, newPlaceId)
		replacePlaceIdInFavorites(oldPlaceId, newPlaceId)
	}

	@Query("UPDATE trip_day_items SET placeId = :newPlaceId WHERE placeId = :oldPlaceId")
	protected abstract fun replacePlaceIdInTrips(oldPlaceId: String, newPlaceId: String)

	@Query("UPDATE favorites SET id = :newPlaceId WHERE id = :oldPlaceId")
	protected abstract fun replacePlaceIdInFavorites(oldPlaceId: String, newPlaceId: String)
}
