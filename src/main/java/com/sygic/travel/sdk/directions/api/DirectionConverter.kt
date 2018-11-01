package com.sygic.travel.sdk.directions.api

import android.graphics.Color
import com.sygic.travel.sdk.directions.model.Direction
import com.sygic.travel.sdk.directions.model.DirectionAttribution
import com.sygic.travel.sdk.directions.model.DirectionLeg
import com.sygic.travel.sdk.directions.model.DirectionLegStop
import com.sygic.travel.sdk.directions.model.DirectionLegTransportType
import com.sygic.travel.sdk.directions.model.DirectionMode
import com.sygic.travel.sdk.directions.model.DirectionTime
import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.utils.DateTimeHelper
import com.sygic.travel.sdk.directions.api.model.ApiDirectionsResponse.Directions.Direction as ApiDirection

internal class DirectionConverter {
	fun fromApi(direction: ApiDirection): Direction? {
		return Direction(
			mode = when (direction.mode) {
				ApiDirection.MODE_CAR -> DirectionMode.CAR
				ApiDirection.MODE_PEDESTRIAN -> DirectionMode.PEDESTRIAN
				ApiDirection.MODE_PUBLIC_TRANSIT -> DirectionMode.PUBLIC_TRANSPORT
				else -> return null
			},
			duration = direction.duration,
			distance = direction.distance,
			transferCount = direction.transfer_count,
			routeId = direction.route_id,
			source = direction.source,
			legs = legsFromApi(direction.legs) ?: return null,
			attributions = direction.attributions.map { attributionFromApi(it) }
		)
	}

	private fun legsFromApi(apiLegs: List<ApiDirection.Legs>): List<DirectionLeg>? {
		return apiLegs.map {
			DirectionLeg(
				startTime = timeFromApi(it.start_time),
				endTime = timeFromApi(it.end_time),
				duration = it.duration,
				distance = it.distance,
				mode = when (it.mode) {
					ApiDirection.Legs.MODE_BIKE -> DirectionLegTransportType.BIKE
					ApiDirection.Legs.MODE_BOAT -> DirectionLegTransportType.BOAT
					ApiDirection.Legs.MODE_BUS -> DirectionLegTransportType.BUS
					ApiDirection.Legs.MODE_CAR -> DirectionLegTransportType.CAR
					ApiDirection.Legs.MODE_FUNICULAR -> DirectionLegTransportType.FUNICULAR
					ApiDirection.Legs.MODE_PEDESTRIAN -> DirectionLegTransportType.PEDESTRIAN
					ApiDirection.Legs.MODE_PLANE -> DirectionLegTransportType.PLANE
					ApiDirection.Legs.MODE_SUBWAY -> DirectionLegTransportType.SUBWAY
					ApiDirection.Legs.MODE_TAXI -> DirectionLegTransportType.TAXI
					ApiDirection.Legs.MODE_TRAIN -> DirectionLegTransportType.TRAIN
					ApiDirection.Legs.MODE_TRAM -> DirectionLegTransportType.TRAM
					else -> return null
				},
				polyline = it.polyline,
				origin = stopFromApi(it.origin),
				destination = stopFromApi(it.destination),
				intermediateStops = it.intermediate_stops.map { stop -> stopFromApi(stop) },
				displayInfo = DirectionLeg.DisplayInfo(
					headsign = it.display_info.headsign,
					nameShort = it.display_info.name_short,
					lineColor = it.display_info.line_color?.let { color -> Color.parseColor(color) },
					displayMode = it.display_info.display_mode
				),
				attribution = it.attribution?.let { attribution -> attributionFromApi(attribution) }
			)
		}
	}

	private fun timeFromApi(time: ApiDirection.Legs.DirectionTime): DirectionTime {
		return DirectionTime(
			datetimeLocal = DateTimeHelper.datetimeLocalToTimestamp(time.datetime_local),
			datetime = DateTimeHelper.datetimeToTimestamp(time.datetime)
		)
	}

	private fun attributionFromApi(attribution: ApiDirection.Attribution): DirectionAttribution {
		return DirectionAttribution(
			name = attribution.name,
			url = attribution.url,
			logoUrl = attribution.logo_url,
			license = attribution.license
		)
	}

	private fun stopFromApi(stop: ApiDirection.Legs.LegStop): DirectionLegStop {
		return DirectionLegStop(
			name = stop.name,
			location = LatLng(stop.location.lat, stop.location.lng),
			arrivalAt = timeFromApi(stop.arrival_at),
			departureAt = timeFromApi(stop.departure_at),
			plannedArrivalAt = timeFromApi(stop.planned_arrival_at),
			plannedDepartureAt = timeFromApi(stop.planned_departure_at)
		)
	}
}
