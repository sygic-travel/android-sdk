package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionMode

class TripItemTransport(
	var mode: DirectionMode
) {
	var avoid = mutableListOf<DirectionAvoid>()
	var startTime: Int? = null
	var duration: Int? = null
	var note: String? = null
	var waypoints = mutableListOf<TripItemTransportWaypoint>()
}
