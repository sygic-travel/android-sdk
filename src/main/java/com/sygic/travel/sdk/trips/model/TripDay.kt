package com.sygic.travel.sdk.trips.model

data class TripDay constructor(
	var note: String? = null,
	var itinerary: List<TripDayItem> = emptyList()
) {
	fun getPlaceIds(): Set<String> {
		return itinerary.map { it.placeId }.toSet()
	}
}
