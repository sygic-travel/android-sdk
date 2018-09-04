package com.sygic.travel.sdk.trips.database.entities

import android.arch.persistence.room.ColumnInfo
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.trips.model.TripItemTransportMode
import com.sygic.travel.sdk.trips.model.TripItemTransportWaypoint

internal class TripDayItemTransport {
	@ColumnInfo
	lateinit var mode: TripItemTransportMode

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
