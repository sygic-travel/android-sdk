package com.sygic.travel.sdk.trips.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sygic.travel.sdk.trips.database.entities.TripDay

@Dao
internal interface TripDaysDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun replaceAll(vararg tripDay: TripDay)

	@Query("DELETE FROM trip_days WHERE trip_id = :tripId AND day_index > :dayIndex")
	fun removeOverDayIndex(tripId: String, dayIndex: Int)

	@Query("SELECT * FROM trip_days WHERE trip_id = :tripId ORDER BY day_index")
	fun findByTripId(tripId: String): List<TripDay>

	@Query("SELECT * FROM trip_days WHERE trip_id IN (:tripIds) ORDER BY trip_id, day_index")
	fun findByTripId(tripIds: List<String>): List<TripDay>

	@Query("DELETE FROM trip_days WHERE trip_id = :tripId")
	fun deleteByTrip(tripId: String)

	@Query("DELETE FROM trip_days")
	fun deleteAll()
}
