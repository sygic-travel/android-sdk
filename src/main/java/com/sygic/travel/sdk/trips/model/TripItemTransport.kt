package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.directions.model.DirectionAvoid
import org.threeten.bp.Duration
import org.threeten.bp.LocalTime

data class TripItemTransport(
	var mode: TripItemTransportMode,
	var avoid: List<DirectionAvoid> = emptyList(),
	var startTime: LocalTime? = null,
	var duration: Duration? = null,
	var note: String? = null,
	var routeId: String? = null,
	var waypoints: List<TripItemTransportWaypoint> = emptyList()
)
