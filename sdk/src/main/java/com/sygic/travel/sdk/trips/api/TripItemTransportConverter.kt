package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionMode
import com.sygic.travel.sdk.places.model.geo.Location
import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.model.TripItemTransport
import com.sygic.travel.sdk.trips.model.TripItemTransportWaypoint

internal class TripItemTransportConverter {
	fun fromApi(apiTransport: ApiTripItemResponse.Day.DayItem.Transport?): TripItemTransport? {
		if (apiTransport == null) {
			return null
		}

		val transport = TripItemTransport(
			mode = when (apiTransport.mode) {
				ApiTripItemResponse.Day.DayItem.Transport.MODE_BIKE -> DirectionMode.BIKE
				ApiTripItemResponse.Day.DayItem.Transport.MODE_BOAT -> DirectionMode.BOAT
				ApiTripItemResponse.Day.DayItem.Transport.MODE_BUS -> DirectionMode.BUS
				ApiTripItemResponse.Day.DayItem.Transport.MODE_CAR -> DirectionMode.CAR
				ApiTripItemResponse.Day.DayItem.Transport.MODE_PEDESTRIAN -> DirectionMode.PEDESTRIAN
				ApiTripItemResponse.Day.DayItem.Transport.MODE_PLANE -> DirectionMode.PLANE
				ApiTripItemResponse.Day.DayItem.Transport.MODE_TRAIN -> DirectionMode.TRAIN
				else -> DirectionMode.PEDESTRIAN
			}
		)
		transport.avoid = ArrayList(apiTransport.avoid.map {
			when (it) {
				ApiTripItemResponse.Day.DayItem.Transport.AVOID_FERRIES -> DirectionAvoid.FERRIES
				ApiTripItemResponse.Day.DayItem.Transport.AVOID_HIGHWAYS -> DirectionAvoid.HIGHWAYS
				ApiTripItemResponse.Day.DayItem.Transport.AVOID_TOLLS -> DirectionAvoid.TOLLS
				ApiTripItemResponse.Day.DayItem.Transport.AVOID_UNPAVED -> DirectionAvoid.UNPAVED
				else -> null
			}
		}.filterNotNull())
		transport.startTime = apiTransport.start_time
		transport.duration = apiTransport.duration
		transport.note = apiTransport.note
		transport.waypoints = ArrayList(apiTransport.waypoints.map {
			TripItemTransportWaypoint(
				placeId = it.placeId,
				location = Location(
					lat = it.location.lat,
					lng = it.location.lng
				)
			)
		})
		return transport
	}

	fun toApi(transport: TripItemTransport?): ApiTripItemResponse.Day.DayItem.Transport? {
		if (transport == null) {
			return null
		}

		return ApiTripItemResponse.Day.DayItem.Transport(
			mode = when (transport.mode) {
				DirectionMode.BIKE -> ApiTripItemResponse.Day.DayItem.Transport.MODE_BIKE
				DirectionMode.BOAT -> ApiTripItemResponse.Day.DayItem.Transport.MODE_BOAT
				DirectionMode.BUS -> ApiTripItemResponse.Day.DayItem.Transport.MODE_BUS
				DirectionMode.CAR -> ApiTripItemResponse.Day.DayItem.Transport.MODE_CAR
				DirectionMode.PEDESTRIAN -> ApiTripItemResponse.Day.DayItem.Transport.MODE_PEDESTRIAN
				DirectionMode.PLANE -> ApiTripItemResponse.Day.DayItem.Transport.MODE_PLANE
				DirectionMode.TRAIN -> ApiTripItemResponse.Day.DayItem.Transport.MODE_TRAIN
			},
			avoid = transport.avoid.map {
				when (it) {
					DirectionAvoid.FERRIES -> ApiTripItemResponse.Day.DayItem.Transport.AVOID_FERRIES
					DirectionAvoid.HIGHWAYS -> ApiTripItemResponse.Day.DayItem.Transport.AVOID_HIGHWAYS
					DirectionAvoid.TOLLS -> ApiTripItemResponse.Day.DayItem.Transport.AVOID_TOLLS
					DirectionAvoid.UNPAVED -> ApiTripItemResponse.Day.DayItem.Transport.AVOID_UNPAVED
				}
			},
			type = "fastest",
			start_time = transport.startTime,
			duration = transport.duration,
			note = transport.note,
			waypoints = transport.waypoints.map {
				ApiTripItemResponse.Day.DayItem.Transport.Waypoint(
					placeId = it.placeId,
					location = ApiTripItemResponse.Day.DayItem.Transport.Waypoint.Location(
						lat = it.location.lat,
						lng = it.location.lng
					)
				)
			}
		)
	}
}
