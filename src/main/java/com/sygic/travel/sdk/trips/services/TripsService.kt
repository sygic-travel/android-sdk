package com.sygic.travel.sdk.trips.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.checkedExecute
import com.sygic.travel.sdk.trips.api.TripConverter
import com.sygic.travel.sdk.trips.database.converters.TripDayDbConverter
import com.sygic.travel.sdk.trips.database.converters.TripDayItemDbConverter
import com.sygic.travel.sdk.trips.database.converters.TripDbConverter
import com.sygic.travel.sdk.trips.database.daos.TripDayItemsDao
import com.sygic.travel.sdk.trips.database.daos.TripDaysDao
import com.sygic.travel.sdk.trips.database.daos.TripsDao
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripBase
import com.sygic.travel.sdk.trips.model.TripInfo
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import com.sygic.travel.sdk.trips.database.entities.Trip as DbTrip
import com.sygic.travel.sdk.trips.database.entities.TripDay as DbTripDay
import com.sygic.travel.sdk.trips.database.entities.TripDayItem as DbTripDayItem

internal class TripsService constructor(
	private val apiClient: SygicTravelApiClient,
	private val tripsDao: TripsDao,
	private val tripDaysDao: TripDaysDao,
	private val tripDayItemsDao: TripDayItemsDao,
	private val tripDbConverter: TripDbConverter,
	private val tripDayDbConverter: TripDayDbConverter,
	private val tripDayItemDbConverter: TripDayItemDbConverter,
	private val tripApiConverter: TripConverter
) {
	fun getTrips(from: LocalDate?, to: LocalDate?, includeOverlapping: Boolean = true): List<TripInfo> {
		val trips = if (includeOverlapping) {
			when {
				from != null && to != null -> tripsDao.findByDatesWithOverlapping(from, to)
				from != null -> tripsDao.findByDateAfterWithOverlapping(from)
				to != null -> tripsDao.findByDateBeforeWithOverlapping(to)
				else -> tripsDao.findAll()
			}
		} else {
			when {
				from != null && to != null -> tripsDao.findByDates(from, to)
				from != null -> tripsDao.findByDateAfter(from)
				to != null -> tripsDao.findByDateBefore(to)
				else -> tripsDao.findAll()
			}
		}

		return trips.map { tripDbConverter.fromAsTripInfo(it) }
	}

	fun getUnscheduledTrips(): List<TripInfo> {
		return tripsDao.findUnscheduled().map { tripDbConverter.fromAsTripInfo(it) }
	}

	fun getDeletedTrips(): List<TripInfo> {
		return tripsDao.findDeleted().map { tripDbConverter.fromAsTripInfo(it) }
	}

	fun getTrip(id: String): Trip? {
		val trip = tripsDao.get(id) ?: return null
		val tripDays = tripDaysDao.findByTripId(id)
		val tripItems = tripDayItemsDao.findByTripId(id)
		return classify(listOf(trip), tripDays, tripItems).firstOrNull()
	}

	fun checkEditPrivilege(trip: TripInfo) {
		check(trip.privileges.edit) { "You cannot save the trip without the edit privilege." }
	}

	fun saveTripAsChanged(trip: TripInfo) {
		if (tripsDao.exists(trip.id) == null) {
			createTrip(trip)
		} else {
			updateTrip(trip)
		}
	}

	fun createTrip(trip: TripInfo) {
		synchronized(trip) {
			val dbTrip = tripDbConverter.to(trip)
			tripsDao.insert(dbTrip)

			if (trip is Trip) {
				saveTripDays(trip)
			}
		}
	}

	fun updateTrip(trip: TripInfo) {
		synchronized(trip) {
			val dbTrip = tripDbConverter.to(trip)
			tripsDao.update(dbTrip)

			if (trip is Trip) {
				saveTripDays(trip)
			}
		}
	}

	private fun saveTripDays(trip: Trip) {
		val dbDays = trip.days.mapIndexed { i, it -> tripDayDbConverter.to(it, i, trip) }
		tripDaysDao.replaceAll(*dbDays.toTypedArray())
		val lastDayIndex = dbDays.lastOrNull()?.dayIndex ?: -1
		tripDaysDao.removeOverDayIndex(trip.id, lastDayIndex)
		tripDayItemsDao.removeOverDayIndex(trip.id, lastDayIndex)

		for ((dayIndex, day) in trip.days.withIndex()) {
			val dbItems = day.itinerary.mapIndexed { itemIndex, it ->
				tripDayItemDbConverter.to(it, itemIndex, dayIndex, trip)
			}
			tripDayItemsDao.replaceAll(*dbItems.toTypedArray())
			tripDayItemsDao.removeOverItemIndex(trip.id, dayIndex, dbItems.lastOrNull()?.itemIndex ?: -1)
		}
	}

	fun deleteTrip(tripId: String) {
		tripsDao.delete(tripId)
	}

	fun emptyTrash() {
		val response = apiClient.deleteTripsInTrash().checkedExecute().body()!!
		for (tripId in response.data!!.deleted_trip_ids) {
			deleteTrip(tripId)
		}
	}

	fun findAllChanged(): List<String> {
		return tripsDao.findAllChanged()
	}

	fun hasChangesToSynchronize(): Boolean {
		return tripsDao.getAllChangedCount() > 0
	}

	fun replaceTripId(trip: Trip, newTripId: String) {
		tripsDao.replaceTripId(trip.id, newTripId)
	}

	fun clearUserData() {
		tripDayItemsDao.deleteAll()
		tripDaysDao.deleteAll()
		tripsDao.deleteAll()
	}

	fun fetchTrip(id: String): Trip? {
		val apiTrip = apiClient.getTrip(id).checkedExecute().body()!!.data!!.trip
		return tripApiConverter.fromApi(apiTrip)
	}

	/**
	 * Connects the loaded date together.
	 * Days has to be ASC-sorted by their day index.
	 * Items has to be ASC-sorted by their day index.
	 */
	private fun classify(dbTrips: List<DbTrip>, dbDays: List<DbTripDay>, dbDayItems: List<DbTripDayItem>): List<Trip> {
		val dbDaysGrouped = dbDays.groupBy { it.tripId }

		val dbDayItemsGrouped = dbDayItems
			.groupBy { it.tripId }
			.mapValues { it.value.groupBy { tripDayItem -> tripDayItem.dayIndex } }

		return dbTrips.map { dbTrip ->
			tripDbConverter.fromAsTrip(
				dbTrip = dbTrip,
				dbDays = dbDaysGrouped[dbTrip.id] ?: emptyList(),
				dbDayItems = dbDayItemsGrouped[dbTrip.id] ?: emptyMap()
			)
		}
	}
}
