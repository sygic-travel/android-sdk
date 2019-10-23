package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.database.entities.TripDay as DbTripDay
import com.sygic.travel.sdk.trips.database.entities.TripDayItem as DbTripDayItem

internal class TripDayDbConverter constructor(
	private val tripDayItemDbConverter: TripDayItemDbConverter
) {
	fun from(dbDay: DbTripDay, items: List<DbTripDayItem>): TripDay {
		return TripDay(
			note = dbDay.note,
			itinerary = items.map { tripDayItemDbConverter.from(it) }
		)
	}

	fun to(day: TripDay, index: Int, trip: Trip): DbTripDay {
		val dbDay = DbTripDay()
		dbDay.note = day.note
		dbDay.tripId = trip.id
		dbDay.dayIndex = index
		return dbDay
	}
}
