package com.sygic.travel.sdk.trips.model

import org.threeten.bp.Duration
import org.threeten.bp.LocalTime

data class TripDayItem constructor(
	var placeId: String,
	var startTime: LocalTime? = null,
	var duration: Duration? = null,
	var note: String? = null,
	var transportFromPrevious: TripItemTransport? = null
)
