package com.sygic.travel.sdk.trips.model

@Suppress("unused")
data class TripDay constructor(
	val note: String? = null,
	val itinerary: List<TripDayItem> = emptyList()
) {
	fun getPlaceIds(): Set<String> {
		return itinerary.map { it.placeId }.toSet()
	}

	fun withItinerary(lambda: (itinerary: MutableList<TripDayItem>) -> List<TripDayItem>): TripDay {
		val itinerary = itinerary.toMutableList()
		return copy(itinerary = lambda.invoke(itinerary).toList())
	}
}
