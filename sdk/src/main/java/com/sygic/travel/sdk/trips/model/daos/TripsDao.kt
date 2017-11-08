package com.sygic.travel.sdk.trips.model.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripInfo

@Dao
interface TripsDao {
	@Query("SELECT * FROM trips")
	fun findAll(): List<TripInfo>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on > :after OR starts_on + days_count * 86400 > :after) AND " +
			"(starts_on < :before OR starts_on + days_count * 86400 > :before)"
	)
	fun findByDates(after: Long, before: Long): List<TripInfo>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on > :after OR starts_on + days_count * 86400 > :after)"
	)
	fun findByDateAfter(after: Long): List<TripInfo>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on < :before OR starts_on + days_count * 86400 > :before)"
	)
	fun findByDateBefore(before: Long): List<TripInfo>

	@Query("SELECT * FROM trips WHERE starts_on IS NULL AND is_deleted = 0")
	fun findUnscheduled(): List<TripInfo>

	@Query("SELECT * FROM trips WHERE is_deleted = 1")
	fun findDeleted(): List<TripInfo>

	@Query("SELECT * FROM trips WHERE id = :id")
	fun get(id: String): Trip?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun replace(trip: TripInfo)

	@Query("SELECT * FROM trips WHERE is_changed = 1 AND id NOT IN (:ids)")
	fun findAllChangedExceptApiChanged(ids: List<String>): List<Trip>

	@Query("DELETE FROM trips WHERE id = :id")
	fun delete(id: String)

	@Query("UPDATE trips SET id = :newId WHERE id = :oldId")
	fun replaceTripId(oldId: String, newId: String)

	@Query("DELETE FROM trips")
	fun deleteAll()
}
