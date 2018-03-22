package com.sygic.travel.sdk.trips.facades

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.services.TripsService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

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

	fun getTrips(from: Long?, to: Long?, includeOverlapping: Boolean = true): List<TripInfo> {
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

	fun getTrip(id: String): Trip? {
		checkNotRunningOnMainThread()
		return tripsService.getTrip(id)
	}

	fun saveTrip(trip: TripInfo) {
		checkNotRunningOnMainThread()
		tripsService.checkEditPrivilege(trip)
		return tripsService.saveTripAsChanged(trip)
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
