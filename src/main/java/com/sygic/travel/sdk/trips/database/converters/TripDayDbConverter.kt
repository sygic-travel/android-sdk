package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.database.entities.TripDay as DbTripDay

internal class TripDayDbConverter {
	fun from(dbDay: DbTripDay): TripDay {
		return TripDay(
			note = dbDay.note
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
