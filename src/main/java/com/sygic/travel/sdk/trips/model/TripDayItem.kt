package com.sygic.travel.sdk.trips.model

class TripDayItem(
	var placeId: String,
	val tripDay: TripDay
) {
	var startTime: Int? = null
	var duration: Int? = null
	var note: String? = null
	var transportFromPrevious: TripItemTransport? = null

	fun getItemIndex(): Int {
		return tripDay.itinerary.indexOf(this)
	}
}
