package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.database.entities.TripDay as DbTripDay

internal class TripDayDbConverter {
	fun from(dbDay: DbTripDay, trip: Trip): TripDay {
		val day = TripDay(trip)
		day.note = dbDay.note
		return day
	}

	fun to(day: TripDay): DbTripDay {
		val dbDay = DbTripDay()
		dbDay.note = day.note
		dbDay.tripId = day.trip.id
		dbDay.dayIndex = day.getDayIndex()
		return dbDay
	}
}
