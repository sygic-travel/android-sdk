package com.sygic.travel.sdk.directions.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.directions.api.DirectionConverter
import com.sygic.travel.sdk.directions.api.model.ApiDirectionRequest
import com.sygic.travel.sdk.directions.api.model.ApiDirectionsResponse
import com.sygic.travel.sdk.directions.model.Direction
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionQuery
import com.sygic.travel.sdk.directions.model.DirectionResponse
import com.sygic.travel.sdk.utils.DateTimeHelper
import com.sygic.travel.sdk.utils.timeSeconds
import kotlin.math.roundToInt

internal class ApiDirectionsService constructor(
	private val apiClient: SygicTravelApiClient,
	private val directionConverter: DirectionConverter
) {
	fun getDirections(requests: List<DirectionQuery>): List<DirectionResponse?> {
		val apiDirections = getCalculatedDirections(requests)
		return apiDirections.mapIndexed { i, it ->
			if (it == null || it.isEmpty()) {
				return@mapIndexed null
			}

			val request = requests[i]
			val airDistance = request.startLocation.distanceTo(request.endLocation).roundToInt()

			return@mapIndexed DirectionResponse(
				startLocation = request.startLocation,
				endLocation = request.endLocation,
				waypoints = request.waypoints,
				avoid = request.avoid,
				airDistance = airDistance,
				directions = it
			)
		}
	}

	private fun getCalculatedDirections(requests: List<DirectionQuery>): List<List<Direction>?> {
		val apiRequests = requests.map {
			ApiDirectionRequest(
				depart_at = DateTimeHelper.timestampToDatetime(it.departAt.timeSeconds),
				arrive_at = DateTimeHelper.timestampToDatetime(it.arriveAt.timeSeconds),
				modes = it.modes?.map { mode -> mode.name },
				origin = ApiDirectionRequest.Location(it.startLocation.lat, it.startLocation.lng),
				destination = ApiDirectionRequest.Location(it.endLocation.lat, it.endLocation.lng),
				avoid = it.avoid.map { avoid ->
					when (avoid) {
						DirectionAvoid.TOLLS -> ApiDirectionRequest.AVOID_TOLLS
						DirectionAvoid.HIGHWAYS -> ApiDirectionRequest.AVOID_HIGHWAYS
						DirectionAvoid.FERRIES -> ApiDirectionRequest.AVOID_FERRIES
						DirectionAvoid.UNPAVED -> ApiDirectionRequest.AVOID_UNPAVED
					}
				},
				waypoints = it.waypoints.map { waypoint ->
					ApiDirectionRequest.Waypoint(
						ApiDirectionRequest.Location(waypoint.lat, waypoint.lng)
					)
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
			it.mapNotNull { innerDirection -> directionConverter.fromApi(innerDirection) }
		}
	}
}
