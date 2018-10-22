package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.directions.model.DirectionAvoid

data class TripItemTransport(
	var mode: TripItemTransportMode,
	var avoid: List<DirectionAvoid> = emptyList(),
	var startTime: Int? = null,
	var duration: Int? = null,
	var note: String? = null,
	var routeId: String? = null,
	var waypoints: List<TripItemTransportWaypoint> = emptyList()
)
