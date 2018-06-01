package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionMode

data class TripItemTransport(
	var mode: DirectionMode,
	var avoid: List<DirectionAvoid> = emptyList(),
	var startTime: Int? = null,
	var duration: Int? = null,
	var note: String? = null,
	var waypoints: List<TripItemTransportWaypoint> = emptyList()
)
