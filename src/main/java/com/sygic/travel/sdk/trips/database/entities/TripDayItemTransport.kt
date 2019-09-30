package com.sygic.travel.sdk.trips.database.entities

import androidx.room.ColumnInfo
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.trips.model.TripItemTransportMode
import com.sygic.travel.sdk.trips.model.TripItemTransportWaypoint
import org.threeten.bp.Duration
import org.threeten.bp.LocalTime

internal class TripDayItemTransport {
	@ColumnInfo
	lateinit var mode: TripItemTransportMode

	@ColumnInfo
	var avoid: ArrayList<DirectionAvoid> = arrayListOf()

	@ColumnInfo(name = "start_time")
	var startTime: LocalTime? = null

	@ColumnInfo
	var duration: Duration? = null

	@ColumnInfo
	var note: String? = null

	@ColumnInfo(name = "route_id")
	var routeId: String? = null

	@ColumnInfo
	var waypoints: ArrayList<TripItemTransportWaypoint> = arrayListOf()
}
