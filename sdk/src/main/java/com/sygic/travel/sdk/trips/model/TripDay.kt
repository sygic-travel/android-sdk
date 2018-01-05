package com.sygic.travel.sdk.trips.model

class TripDay(
	val trip: Trip
) {
	var note: String? = null
	var itinerary = mutableListOf<TripDayItem>()

	fun getDayIndex(): Int {
		return trip.days.indexOf(this)
	}

	fun getPlaceIds(): Set<String> {
		return itinerary.map { it.placeId }.toSet()
	}
}
