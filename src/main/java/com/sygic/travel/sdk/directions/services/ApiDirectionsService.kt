package com.sygic.travel.sdk.directions.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.directions.api.model.ApiDirectionRequest
import com.sygic.travel.sdk.directions.api.model.ApiDirectionsResponse
import com.sygic.travel.sdk.directions.helpers.AirDistanceCalculator
import com.sygic.travel.sdk.directions.model.Direction
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionMode
import com.sygic.travel.sdk.directions.model.Directions
import com.sygic.travel.sdk.directions.model.DirectionsRequest

internal class ApiDirectionsService constructor(
	private val apiClient: SygicTravelApiClient,
	private val naiveDirectionsService: NaiveDirectionsService
) {
	fun getDirections(requests: List<DirectionsRequest>): List<Directions?> {
		val directions = getCalculatedDirections(requests)
		return directions.mapIndexed { i, it ->
			if (it.isEmpty()) {
				return@mapIndexed null
			}

			val airDistance = AirDistanceCalculator.getAirDistance(requests[i].from, requests[i].to)
			val pedestrian = arrayListOf<Direction>()
			val car = arrayListOf<Direction>()
			val plane = arrayListOf<Direction>()

			if (airDistance <= DirectionsService.PEDESTRIAN_MAX_LIMIT) {
				it.filterTo(pedestrian, { it2 -> it2.mode == DirectionMode.PEDESTRIAN })
			}
			if (airDistance <= DirectionsService.CAR_MAX_LIMIT) {
				it.filterTo(car, { it2 -> it2.mode == DirectionMode.CAR })
			}
			if (airDistance > DirectionsService.PLANE_MIN_LIMIT) {
				plane.add(naiveDirectionsService.getPlaneDirection(airDistance))
			}

			if (pedestrian.isEmpty() && car.isEmpty()) {
				return@mapIndexed null
			} else {
				return@mapIndexed Directions(
					airDistance = airDistance,
					pedestrian = pedestrian,
					car = car,
					plane = plane
				)
			}
		}
	}

	private fun getCalculatedDirections(requests: List<DirectionsRequest>): List<List<Direction>> {
		val routes = arrayListOf<ApiDirectionRequest>()
		for (directionRequest in requests) {
			val firstLocation = directionRequest.from
			val secondLocation = directionRequest.to

			routes.add(ApiDirectionRequest(
				origin = ApiDirectionRequest.Location(firstLocation.lat, firstLocation.lng),
				destination = ApiDirectionRequest.Location(secondLocation.lat, secondLocation.lng),
				avoid = directionRequest.avoid.map {
					when (it) {
						DirectionAvoid.TOLLS -> ApiDirectionRequest.AVOID_TOLLS
						DirectionAvoid.HIGHWAYS -> ApiDirectionRequest.AVOID_HIGHWAYS
						DirectionAvoid.FERRIES -> ApiDirectionRequest.AVOID_FERRIES
						DirectionAvoid.UNPAVED -> ApiDirectionRequest.AVOID_UNPAVED
					}
				},
				waypoints = directionRequest.waypoints.map {
					ApiDirectionRequest.Location(it.lat, it.lng)
				}
			))
		}

		val response: ApiResponse<ApiDirectionsResponse>
		try {
			response = apiClient.getDirections(routes).execute().body()!!
		} catch (_: Exception) {
			return emptyList()
		}

		val directions = response.data!!.path.map { it.directions }
		assert(directions.size == requests.size)

		return directions.map {
			it.filter { it.enumMode != null }.map {
				Direction(
					mode = it.enumMode!!,
					duration = it.duration,
					distance = it.distance,
					polyline = it.polyline,
					isEstimated = false
				)
			}
		}
	}
}
