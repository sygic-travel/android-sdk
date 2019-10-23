package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.directions.model.DirectionAvoid
import org.threeten.bp.Duration
import org.threeten.bp.LocalTime

data class TripItemTransport(
	val mode: TripItemTransportMode,
	val avoid: List<DirectionAvoid> = emptyList(),
	val startTime: LocalTime? = null,
	val duration: Duration? = null,
	val note: String? = null,
	val routeId: String? = null,
	val waypoints: List<TripItemTransportWaypoint> = emptyList()
)
