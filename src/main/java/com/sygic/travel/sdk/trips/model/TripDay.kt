package com.sygic.travel.sdk.trips.model

data class TripDay constructor(
	val note: String? = null,
	val itinerary: List<TripDayItem> = emptyList()
) {
	fun getPlaceIds(): Set<String> {
		return itinerary.map { it.placeId }.toSet()
	}
}
