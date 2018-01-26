package com.sygic.travel.sdk.trips.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.trips.database.converters.TripDayDbConverter
import com.sygic.travel.sdk.trips.database.converters.TripDayItemDbConverter
import com.sygic.travel.sdk.trips.database.converters.TripDbConverter
import com.sygic.travel.sdk.trips.database.daos.TripDayItemsDao
import com.sygic.travel.sdk.trips.database.daos.TripDaysDao
import com.sygic.travel.sdk.trips.database.daos.TripsDao
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.utils.DateTimeHelper
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
	private val tripDayItemDbConverter: TripDayItemDbConverter
) {
	fun getTrips(from: Long?, to: Long?, includeOverlapping: Boolean = true): List<TripInfo> {
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
		if (!trip.privileges.edit) {
			throw IllegalStateException("You cannot save the trip without the edit privilege.")
		}
	}

	fun saveTripAsChanged(trip: Trip) {
		trip.isChanged = true
		trip.updatedAt = DateTimeHelper.now()
		saveTrip(trip)
	}

	fun saveTripAsChanged(trip: TripInfo) {
		trip.isChanged = true
		trip.updatedAt = DateTimeHelper.now()
		saveTrip(trip)
	}

	fun saveTrip(trip: Trip) {
		synchronized(trip) {
			val dbTrip = tripDbConverter.to(trip)
			tripsDao.replace(dbTrip)

			val dbDays = trip.days.map {
				tripDayDbConverter.to(it)
			}
			tripDaysDao.replaceAll(*dbDays.toTypedArray())
			tripDaysDao.removeOverDayIndex(trip.id, dbDays.lastOrNull()?.dayIndex ?: -1)

			for ((dayIndex, day) in trip.days.withIndex()) {
				val dbItems = day.itinerary.map {
					tripDayItemDbConverter.to(it)
				}
				tripDayItemsDao.replaceAll(*dbItems.toTypedArray())
				tripDayItemsDao.removeOverItemIndex(trip.id, dayIndex, dbItems.lastOrNull()?.itemIndex ?: -1)
			}
		}
	}

	fun saveTrip(trip: TripInfo) {
		val dbTrip = tripDbConverter.to(trip)
		tripsDao.replace(dbTrip)
	}

	fun deleteTrip(tripId: String) {
		tripsDao.delete(tripId)
	}

	fun emptyTrash() {
		val response = apiClient.deleteTripsInTrash().execute().body()!!
		for (tripId in response.data!!.deleted_trip_ids) {
			deleteTrip(tripId)
		}
	}

	fun findAllChangedExceptApiChanged(apiChangedTripIds: List<String>): List<Trip> {
		val trips = tripsDao.findAllChangedExceptApiChanged(apiChangedTripIds)
		val ids = trips.map { it.id }
		val tripDays = tripDaysDao.findByTripId(ids)
		val tripItems = tripDayItemsDao.findByTripId(ids)
		return classify(trips, tripDays, tripItems)
	}

	fun hasChangesToSynchronize(): Boolean {
		return tripsDao.getAllChangedCount() > 0
	}

	fun replaceTripId(trip: Trip, newTripId: String) {
		tripsDao.replaceTripId(trip.id, newTripId)
		trip.id = newTripId
	}

	fun clearUserData() {
		tripDayItemsDao.deleteAll()
		tripDaysDao.deleteAll()
		tripsDao.deleteAll()
	}

	/**
	 * Connects the loaded date together.
	 * Days has to be sorted ASC by their day index.
	 * Items has to be sorted ASC by their day index.
	 */
	private fun classify(dbTrips: List<DbTrip>, dbDays: List<DbTripDay>, dbItems: List<DbTripDayItem>): List<Trip> {
		val trips = dbTrips.map { tripDbConverter.fromAsTrip(it) }
		val tripsMap = trips.associateBy { it.id }

		dbDays
			.groupBy { it.tripId }
			.forEach {
				val trip = tripsMap[it.key]!!
				trip.days = it.value.map {
					tripDayDbConverter.from(it, trip)
				}
			}

		dbItems
			.groupBy { it.tripId }
			.mapValues { it.value.groupBy { it.dayIndex } }
			.forEach {
				val trip = tripsMap[it.key]!!
				it.value.forEach {
					val day = trip.days[it.key]
					day.itinerary = it.value.map {
						tripDayItemDbConverter.from(it, day)
					}
				}
			}

		return trips
	}
}
