package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.database.entities.TripDay as DbTripDay

internal class TripDayDbConverter {
	fun from(dbDay: DbTripDay): TripDay {
		val day = TripDay()
		day.note = dbDay.note
		return day
	}

	fun to(day: TripDay, tripId: String, dayIndex: Int): DbTripDay {
		val dbDay = DbTripDay()
		dbDay.note = day.note
		dbDay.tripId = tripId
		dbDay.dayIndex = dayIndex
		return dbDay
	}
}
