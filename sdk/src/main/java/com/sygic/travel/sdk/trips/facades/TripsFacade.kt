package com.sygic.travel.sdk.trips.facades

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.services.TripsService
import com.sygic.travel.sdk.utils.runAsync

@Suppress("unused")
class TripsFacade(
	private val tripsService: TripsService
) {
	suspend fun getTrips(from: Long?, to: Long?): List<TripInfo> {
		return runAsync { tripsService.getTrips(from, to) }
	}

	suspend fun getFutureTrips(): List<TripInfo> {
		return runAsync { tripsService.getFutureTrips() }
	}

	suspend fun getPastTrips(): List<TripInfo> {
		return runAsync { tripsService.getPastTrips() }
	}

	suspend fun getCurrentTrips(): List<TripInfo> {
		return runAsync { tripsService.getCurrentTrips() }
	}

	suspend fun getUnscheduledTrips(): List<TripInfo> {
		return runAsync { tripsService.getUnscheduledTrips() }
	}

	suspend fun getDeletedTrips(): List<TripInfo> {
		return runAsync { tripsService.getDeletedTrips() }
	}

	suspend fun getTrip(id: String): Trip? {
		return runAsync { tripsService.getTrip(id) }
	}

	suspend fun saveTrip(trip: Trip) {
		return runAsync { tripsService.saveTrip(trip) }
	}

	suspend fun saveTrip(trip: TripInfo) {
		return runAsync { tripsService.saveTrip(trip) }
	}

	suspend fun deleteTrip(trip: TripInfo) {
		return runAsync { tripsService.deleteTrip(trip.id) }
	}

	suspend fun emptyTripTrash() {
		return runAsync { tripsService.emptyTrash() }
	}
}
