package com.sygic.travel.sdk.trips.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sygic.travel.sdk.trips.database.entities.Trip

@Dao
internal interface TripsDao {
	@Query("SELECT * FROM trips WHERE is_deleted = 0")
	fun findAll(): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on > :from) AND " +
			"(starts_on + days_count * 86400 < :to)"
	)
	fun findByDates(from: Long, to: Long): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on > :from OR starts_on + days_count * 86400 > :from) AND " +
			"(starts_on < :to OR starts_on + days_count * 86400 < :to)"
	)
	fun findByDatesWithOverlapping(from: Long, to: Long): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on > :from)"
	)
	fun findByDateAfter(from: Long): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on > :from OR starts_on + days_count * 86400 > :from)"
	)
	fun findByDateAfterWithOverlapping(from: Long): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on + days_count * 86400 < :to)"
	)
	fun findByDateBefore(to: Long): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on < :to OR starts_on + days_count * 86400 < :to)"
	)
	fun findByDateBeforeWithOverlapping(to: Long): List<Trip>

	@Query("SELECT * FROM trips WHERE starts_on IS NULL AND is_deleted = 0")
	fun findUnscheduled(): List<Trip>

	@Query("SELECT * FROM trips WHERE is_deleted = 1")
	fun findDeleted(): List<Trip>

	@Query("SELECT * FROM trips WHERE id = :id")
	fun get(id: String): Trip?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun replace(trip: Trip)

	@Query("SELECT * FROM trips WHERE is_changed = 1 AND id NOT IN (:ids)")
	fun findAllChangedExceptApiChanged(ids: List<String>): List<Trip>

	@Query("SELECT COUNT(*) FROM trips WHERE is_changed = 1")
	fun getAllChangedCount(): Int

	@Query("DELETE FROM trips WHERE id = :id")
	fun delete(id: String)

	@Query("UPDATE trips SET id = :newId WHERE id = :oldId")
	fun replaceTripId(oldId: String, newId: String)

	@Query("DELETE FROM trips")
	fun deleteAll()
}
