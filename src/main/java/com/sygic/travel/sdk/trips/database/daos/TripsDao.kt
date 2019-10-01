package com.sygic.travel.sdk.trips.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sygic.travel.sdk.trips.database.entities.Trip
import org.threeten.bp.LocalDate

@Dao
internal interface TripsDao {
	@Query("SELECT * FROM trips WHERE is_deleted = 0")
	fun findAll(): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on >= :from) AND " +
			"(starts_on + (days_count - 1) * 86400 <= :to)"
	)
	fun findByDates(from: LocalDate, to: LocalDate): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on >= :from OR starts_on + (days_count - 1) * 86400 >= :from) AND " +
			"(starts_on <= :to OR starts_on + (days_count - 1) * 86400 <= :to)"
	)
	fun findByDatesWithOverlapping(from: LocalDate, to: LocalDate): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on >= :from)"
	)
	fun findByDateAfter(from: LocalDate): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on >= :from OR starts_on + (days_count - 1) * 86400 >= :from)"
	)
	fun findByDateAfterWithOverlapping(from: LocalDate): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on + (days_count - 1) * 86400 <= :to)"
	)
	fun findByDateBefore(to: LocalDate): List<Trip>

	@Query(
		"SELECT * FROM trips WHERE starts_on IS NOT NULL AND is_deleted = 0 AND " +
			"(starts_on <= :to OR starts_on + (days_count - 1) * 86400 <= :to)"
	)
	fun findByDateBeforeWithOverlapping(to: LocalDate): List<Trip>

	@Query("SELECT * FROM trips WHERE starts_on IS NULL AND is_deleted = 0")
	fun findUnscheduled(): List<Trip>

	@Query("SELECT * FROM trips WHERE is_deleted = 1")
	fun findDeleted(): List<Trip>

	@Query("SELECT * FROM trips WHERE id = :id")
	fun get(id: String): Trip?

	@Insert
	fun insert(trip: Trip)

	@Update
	fun update(trip: Trip)

	@Query("SELECT id FROM trips WHERE is_changed = 1")
	fun findAllChanged(): List<String>

	@Query("SELECT COUNT(*) FROM trips WHERE is_changed = 1")
	fun getAllChangedCount(): Int

	@Query("SELECT id FROM trips WHERE id = :id")
	fun exists(id: String): String?

	@Query("DELETE FROM trips WHERE id = :id")
	fun delete(id: String)

	@Query("UPDATE trips SET id = :newId WHERE id = :oldId")
	fun replaceTripId(oldId: String, newId: String)

	@Query("DELETE FROM trips")
	fun deleteAll()
}
