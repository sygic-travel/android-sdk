package com.sygic.travel.sdk.directions.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.directions.api.model.ApiDirectionRequest
import com.sygic.travel.sdk.directions.api.model.ApiDirectionsResponse
import com.sygic.travel.sdk.directions.helpers.AirDistanceCalculator
import com.sygic.travel.sdk.directions.model.Direction
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionMode
import com.sygic.travel.sdk.directions.model.DirectionResponse
import com.sygic.travel.sdk.directions.model.DirectionRequest

internal class ApiDirectionsService constructor(
	private val apiClient: SygicTravelApiClient,
	private val estimatedDirectionsService: EstimatedDirectionsService
) {
	fun getDirections(requests: List<DirectionRequest>): List<DirectionResponse?> {
		val apiDirections = getCalculatedDirections(requests)
		return apiDirections.mapIndexed { i, it ->
			if (it == null || it.isEmpty()) {
				return@mapIndexed null
			}

			val request = requests[i]
			val airDistance = AirDistanceCalculator.getAirDistance(request.startLocation, request.endLocation)
			val directions = mutableListOf<Direction>()

			if (airDistance <= DirectionsService.PEDESTRIAN_MAX_LIMIT) {
				it.filterTo(directions, { it2 -> it2.mode == DirectionMode.PEDESTRIAN })
			}
			if (airDistance <= DirectionsService.CAR_MAX_LIMIT) {
				it.filterTo(directions, { it2 -> it2.mode == DirectionMode.CAR })
			}
			if (airDistance > DirectionsService.PLANE_MIN_LIMIT) {
				directions.add(estimatedDirectionsService.getPlaneDirection(airDistance))
			}

			return@mapIndexed DirectionResponse(
				startLocation = request.startLocation,
				endLocation = request.endLocation,
				waypoints = request.waypoints,
				avoid = request.avoid,
				airDistance = airDistance,
				results = directions
			)
		}
	}

	private fun getCalculatedDirections(requests: List<DirectionRequest>): List<List<Direction>?> {
		val apiRequests = requests.map {
			ApiDirectionRequest(
				origin = ApiDirectionRequest.Location(it.startLocation.lat, it.startLocation.lng),
				destination = ApiDirectionRequest.Location(it.endLocation.lat, it.endLocation.lng),
				avoid = it.avoid.map {
					when (it) {
						DirectionAvoid.TOLLS -> ApiDirectionRequest.AVOID_TOLLS
						DirectionAvoid.HIGHWAYS -> ApiDirectionRequest.AVOID_HIGHWAYS
						DirectionAvoid.FERRIES -> ApiDirectionRequest.AVOID_FERRIES
						DirectionAvoid.UNPAVED -> ApiDirectionRequest.AVOID_UNPAVED
					}
				},
				waypoints = it.waypoints.map {
					ApiDirectionRequest.Location(it.lat, it.lng)
				}
			)
		}

		val response: ApiResponse<ApiDirectionsResponse>
		try {
			response = apiClient.getDirections(apiRequests).execute().body()!!
		} catch (_: Exception) {
			return (0 until requests.size).map { null }
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
