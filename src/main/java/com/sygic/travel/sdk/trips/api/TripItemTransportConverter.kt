package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.model.TripItemTransport
import com.sygic.travel.sdk.trips.model.TripItemTransportMode
import com.sygic.travel.sdk.trips.model.TripItemTransportWaypoint

internal class TripItemTransportConverter {
	fun fromApi(apiTransport: ApiTripItemResponse.Day.DayItem.Transport?): TripItemTransport? {
		if (apiTransport == null) {
			return null
		}

		return TripItemTransport(
			mode = when (apiTransport.mode) {
				ApiTripItemResponse.Day.DayItem.Transport.MODE_BIKE -> TripItemTransportMode.BIKE
				ApiTripItemResponse.Day.DayItem.Transport.MODE_BOAT -> TripItemTransportMode.BOAT
				ApiTripItemResponse.Day.DayItem.Transport.MODE_BUS -> TripItemTransportMode.BUS
				ApiTripItemResponse.Day.DayItem.Transport.MODE_CAR -> TripItemTransportMode.CAR
				ApiTripItemResponse.Day.DayItem.Transport.MODE_PEDESTRIAN -> TripItemTransportMode.PEDESTRIAN
				ApiTripItemResponse.Day.DayItem.Transport.MODE_PLANE -> TripItemTransportMode.PLANE
				ApiTripItemResponse.Day.DayItem.Transport.MODE_PUBLIC_TRANSIT -> TripItemTransportMode.PUBLIC_TRANSPORT
				ApiTripItemResponse.Day.DayItem.Transport.MODE_TRAIN -> TripItemTransportMode.TRAIN
				else -> TripItemTransportMode.PEDESTRIAN
			},
			avoid = ArrayList(apiTransport.avoid.mapNotNull {
				when (it) {
					ApiTripItemResponse.Day.DayItem.Transport.AVOID_FERRIES -> DirectionAvoid.FERRIES
					ApiTripItemResponse.Day.DayItem.Transport.AVOID_HIGHWAYS -> DirectionAvoid.HIGHWAYS
					ApiTripItemResponse.Day.DayItem.Transport.AVOID_TOLLS -> DirectionAvoid.TOLLS
					ApiTripItemResponse.Day.DayItem.Transport.AVOID_UNPAVED -> DirectionAvoid.UNPAVED
					else -> null
				}
			}),
			startTime = apiTransport.start_time,
			duration = apiTransport.duration,
			note = apiTransport.note,
			waypoints = ArrayList(apiTransport.waypoints.map {
				TripItemTransportWaypoint(
					placeId = it.place_id,
					location = LatLng(
						lat = it.location.lat,
						lng = it.location.lng
					)
				)
			})
		)
	}

	fun toApi(transport: TripItemTransport?): ApiTripItemResponse.Day.DayItem.Transport? {
		if (transport == null) {
			return null
		}

		return ApiTripItemResponse.Day.DayItem.Transport(
			mode = when (transport.mode) {
				TripItemTransportMode.BIKE -> ApiTripItemResponse.Day.DayItem.Transport.MODE_BIKE
				TripItemTransportMode.BOAT -> ApiTripItemResponse.Day.DayItem.Transport.MODE_BOAT
				TripItemTransportMode.BUS -> ApiTripItemResponse.Day.DayItem.Transport.MODE_BUS
				TripItemTransportMode.CAR -> ApiTripItemResponse.Day.DayItem.Transport.MODE_CAR
				TripItemTransportMode.PEDESTRIAN -> ApiTripItemResponse.Day.DayItem.Transport.MODE_PEDESTRIAN
				TripItemTransportMode.PLANE -> ApiTripItemResponse.Day.DayItem.Transport.MODE_PLANE
				TripItemTransportMode.PUBLIC_TRANSPORT -> ApiTripItemResponse.Day.DayItem.Transport.MODE_PUBLIC_TRANSIT
				TripItemTransportMode.TRAIN -> ApiTripItemResponse.Day.DayItem.Transport.MODE_TRAIN
			},
			avoid = transport.avoid.map {
				when (it) {
					DirectionAvoid.FERRIES -> ApiTripItemResponse.Day.DayItem.Transport.AVOID_FERRIES
					DirectionAvoid.HIGHWAYS -> ApiTripItemResponse.Day.DayItem.Transport.AVOID_HIGHWAYS
					DirectionAvoid.TOLLS -> ApiTripItemResponse.Day.DayItem.Transport.AVOID_TOLLS
					DirectionAvoid.UNPAVED -> ApiTripItemResponse.Day.DayItem.Transport.AVOID_UNPAVED
				}
			},
			start_time = transport.startTime,
			duration = transport.duration,
			note = transport.note,
			waypoints = transport.waypoints.map {
				ApiTripItemResponse.Day.DayItem.Transport.Waypoint(
					place_id = it.placeId,
					location = ApiTripItemResponse.Day.DayItem.Transport.Waypoint.Location(
						lat = it.location.lat,
						lng = it.location.lng
					)
				)
			}
		)
	}
}
