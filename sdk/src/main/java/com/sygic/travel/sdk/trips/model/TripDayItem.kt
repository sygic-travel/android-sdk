package com.sygic.travel.sdk.trips.model

class TripDayItem(
	var placeId: String
) {
	var startTime: Int? = null
	var duration: Int? = null
	var note: String? = null
	var transportFromPrevious: TripItemTransport? = null
}
