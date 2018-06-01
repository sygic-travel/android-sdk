package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.TripItemTransport
import com.sygic.travel.sdk.trips.database.entities.TripDayItemTransport as DbTripItemTransport

internal class TripDayItemTransportDbConverter {
	fun from(dbTransport: DbTripItemTransport?): TripItemTransport? {
		dbTransport ?: return null
		return TripItemTransport(
			mode = dbTransport.mode,
			avoid = dbTransport.avoid,
			startTime = dbTransport.startTime,
			duration = dbTransport.duration,
			note = dbTransport.note,
			waypoints = dbTransport.waypoints
		)
	}

	fun to(transport: TripItemTransport?): DbTripItemTransport? {
		transport ?: return null
		val dbTransport = DbTripItemTransport()
		dbTransport.mode = transport.mode
		dbTransport.avoid = ArrayList(transport.avoid)
		dbTransport.startTime = transport.startTime
		dbTransport.duration = transport.duration
		dbTransport.note = transport.note
		dbTransport.waypoints = ArrayList(transport.waypoints)
		return dbTransport
	}
}
