package com.sygic.travel.sdk.trips.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.model.TripDayItem
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.model.daos.TripDayItemsDao
import com.sygic.travel.sdk.trips.model.daos.TripDaysDao
import com.sygic.travel.sdk.trips.model.daos.TripsDao
import com.sygic.travel.sdk.utils.DateTimeHelper
import java.util.Date

class TripsService constructor(
	private val apiClient: SygicTravelApiClient,
	private val tripsDao: TripsDao,
	private val tripDaysDao: TripDaysDao,
	private val tripDayItemsDao: TripDayItemsDao
) {
	fun getTrips(from: Long?, to: Long?): List<TripInfo> {
		return when {
			from != null && to != null -> tripsDao.findByDates(from, to)
			from != null -> tripsDao.findByDateAfter(from)
			to != null -> tripsDao.findByDateBefore(to)
			else -> tripsDao.findAll()
		}
	}

	fun getFutureTrips(): List<TripInfo> {
		return tripsDao.findByDateAfter(DateTimeHelper.tomorrow() - 1)
	}

	fun getPastTrips(): List<TripInfo> {
		return tripsDao.findByDateBefore(DateTimeHelper.today())
	}

	fun getCurrentTrips(): List<TripInfo> {
		return tripsDao.findByDates(DateTimeHelper.today(), DateTimeHelper.tomorrow())
	}

	fun getUnscheduledTrips(): List<TripInfo> {
		return tripsDao.findUnscheduled()
	}

	fun getDeletedTrips(): List<TripInfo> {
		return tripsDao.findDeleted()
	}

	fun getTrip(id: String): Trip? {
		val trip = tripsDao.get(id) ?: return null
		val tripDays = tripDaysDao.findById(id)
		val tripItems = tripDayItemsDao.findById(id)
		classify(arrayListOf(trip), tripDays, tripItems)
		return trip
	}

	fun saveTrip(trip: Trip) {
		synchronized(trip) {
			trip.reindexDays()
			tripsDao.replace(trip)
			tripDaysDao.replaceAll(*trip.days.toTypedArray())
			for (day in trip.days.iterator()) {
				tripDayItemsDao.replaceAll(*day.itinerary.toTypedArray())
				tripDayItemsDao.removeOverItemIndex(trip.id, day.dayIndex, day.itinerary.lastOrNull()?.itemIndex ?: -1)
			}
			tripDaysDao.removeOverDayIndex(trip.id, trip.days.lastOrNull()?.dayIndex ?: -1)
		}
	}

	fun saveTrip(trip: TripInfo) {
		tripsDao.replace(trip)
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
		val tripDays = tripDaysDao.findById(ids)
		val tripItems = tripDayItemsDao.findById(ids)
		classify(trips, tripDays, tripItems)
		return trips
	}

	fun replaceTripId(trip: Trip, newTripId: String) {
		tripsDao.replaceTripId(trip.id, newTripId)
		trip.id = newTripId
		trip.reindexDays()
	}

	/**
	 * Connects the loaded date together.
	 * Days has to be sorted ASC by their day index.
	 * Items has to be sorted ASC by their day index.
	 */
	private fun classify(trips: List<Trip>, days: List<TripDay>, items: List<TripDayItem>) {
		val map = trips.associateBy { it.id }
		trips.forEach {
			it.days = arrayListOf()
		}
		for (day in days) {
			map[day.tripId]!!.days.add(day)
			day.trip = map[day.tripId]!!
		}
		for (item in items) {
			map[item.tripId]!!.days[item.dayIndex].itinerary.add(item)
		}
	}
}
