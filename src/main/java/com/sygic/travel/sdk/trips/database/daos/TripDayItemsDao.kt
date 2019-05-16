package com.sygic.travel.sdk.trips.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sygic.travel.sdk.trips.database.entities.TripDayItem

@Dao
internal interface TripDayItemsDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun replaceAll(vararg tripDayItem: TripDayItem)

	@Query("DELETE FROM trip_day_items WHERE trip_id = :tripId AND day_index > :dayIndex")
	fun removeOverDayIndex(tripId: String, dayIndex: Int)

	@Query("DELETE FROM trip_day_items WHERE trip_id = :tripId AND day_index = :dayIndex AND item_index > :itemIndex")
	fun removeOverItemIndex(tripId: String, dayIndex: Int, itemIndex: Int)

	@Query("SELECT * FROM trip_day_items WHERE trip_id = :tripId ORDER BY day_index, item_index")
	fun findByTripId(tripId: String): List<TripDayItem>

	@Query("SELECT * FROM trip_day_items WHERE trip_id IN (:tripIds) ORDER BY trip_id, day_index, item_index")
	fun findByTripId(tripIds: List<String>): List<TripDayItem>

	@Query("DELETE FROM trip_day_items WHERE trip_id = :tripId")
	fun deleteByTrip(tripId: String)

	@Query("DELETE FROM trip_day_items")
	fun deleteAll()
}
