package com.sygic.travel.sdk.trips.model

class TripDay {
	var note: String? = null
	var itinerary = mutableListOf<TripDayItem>()

	fun getPlaceIds(): Set<String> {
		return itinerary.map { it.placeId }.toSet()
	}
}
