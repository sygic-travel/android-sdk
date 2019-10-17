package com.sygic.travel.sdk.trips.model

import org.threeten.bp.Duration
import org.threeten.bp.LocalTime

data class TripDayItem constructor(
	val placeId: String,
	val startTime: LocalTime? = null,
	val duration: Duration? = null,
	val note: String? = null,
	val transportFromPrevious: TripItemTransport? = null
)
