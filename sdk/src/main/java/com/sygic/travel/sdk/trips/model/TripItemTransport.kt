package com.sygic.travel.sdk.trips.model

import android.arch.persistence.room.ColumnInfo
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionMode

class TripItemTransport {
	@ColumnInfo
	lateinit var mode: DirectionMode

	@ColumnInfo
	var avoid: ArrayList<DirectionAvoid> = arrayListOf()

	@ColumnInfo(name = "start_time")
	var startTime: Int? = null

	@ColumnInfo
	var duration: Int? = null

	@ColumnInfo
	var note: String? = null

	@ColumnInfo
	var waypoints: ArrayList<TripItemTransportWaypoint> = arrayListOf()
}
