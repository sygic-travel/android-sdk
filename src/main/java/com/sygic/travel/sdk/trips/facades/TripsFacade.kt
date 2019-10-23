package com.sygic.travel.sdk.trips.facades

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripBase
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.services.TripsService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate

/**
 * Trips facade provides interface for managing & processing user trips.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class TripsFacade internal constructor(
	private val tripsService: TripsService
) {
	fun getAllTrips(): List<TripInfo> {
		checkNotRunningOnMainThread()
		return tripsService.getTrips(null, null)
	}

	fun getTrips(from: LocalDate?, to: LocalDate?, includeOverlapping: Boolean = true): List<TripInfo> {
		checkNotRunningOnMainThread()
		return tripsService.getTrips(from, to, includeOverlapping)
	}

	fun getUnscheduledTrips(): List<TripInfo> {
		checkNotRunningOnMainThread()
		return tripsService.getUnscheduledTrips()
	}

	fun getDeletedTrips(): List<TripInfo> {
		checkNotRunningOnMainThread()
		return tripsService.getDeletedTrips()
	}

	/**
	 * Fetches trip from API without storing it into local db.
	 */
	fun fetchTrip(id: String): Trip? {
		checkNotRunningOnMainThread()
		return tripsService.fetchTrip(id)
	}

	fun getTrip(id: String): Trip? {
		checkNotRunningOnMainThread()
		return tripsService.getTrip(id)
	}

	fun saveTrip(trip: TripBase): TripBase {
		checkNotRunningOnMainThread()
		tripsService.checkEditPrivilege(trip)
		val changedTrip = trip.copy(
			isChanged = true,
			updatedAt = Instant.now()
		)
		tripsService.saveTripAsChanged(changedTrip)
		return changedTrip
	}

	fun saveTrip(trip: Trip): Trip {
		checkNotRunningOnMainThread()
		tripsService.checkEditPrivilege(trip)
		val changedTrip = trip.copy(
			isChanged = true,
			updatedAt = Instant.now()
		)
		tripsService.saveTripAsChanged(changedTrip)
		return changedTrip
	}

	/**
	 * Imports trip into the storage.
	 * This method is internal and should not be used.
	 */
	fun importTrip(trip: Trip) {
		checkNotRunningOnMainThread()
		tripsService.createTrip(trip)
	}

	fun emptyTripTrash() {
		checkNotRunningOnMainThread()
		return tripsService.emptyTrash()
	}

	internal fun clearUserData() {
		checkNotRunningOnMainThread()
		tripsService.clearUserData()
	}
}
