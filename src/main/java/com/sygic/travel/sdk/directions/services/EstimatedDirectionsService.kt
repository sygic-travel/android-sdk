package com.sygic.travel.sdk.directions.services

import com.sygic.travel.sdk.directions.model.Direction
import com.sygic.travel.sdk.directions.model.DirectionMode
import com.sygic.travel.sdk.directions.model.DirectionRequest
import com.sygic.travel.sdk.directions.model.DirectionResponse
import kotlin.math.roundToInt

internal class EstimatedDirectionsService {
	companion object {
		private const val PEDESTRIAN_MAX_LIMIT = 20_000
		private const val CAR_MAX_LIMIT = 2_000_000
		private const val PLANE_MIN_LIMIT = 100_000
		private const val FALLBACK_DISTANCE_PEDESTRIAN_1 = 1.35
		private const val FALLBACK_DISTANCE_PEDESTRIAN_2 = 1.22
		private const val FALLBACK_DISTANCE_PEDESTRIAN_3 = 1.106
		private const val FALLBACK_DISTANCE_CAR_1 = 1.8
		private const val FALLBACK_DISTANCE_CAR_2 = 1.6
		private const val FALLBACK_DISTANCE_CAR_3 = 1.2
		private const val FALLBACK_SPEED_PEDESTRIAN = 1.3333 // 4.8 km/h
		private const val FALLBACK_SPEED_CAR_1 = 7.5 // 27 km/h
		private const val FALLBACK_CAR_SPEED_2 = 15.0 // 54 km/h
		private const val FALLBACK_CAR_SPEED_3 = 25.0 // 90 km/h
		private const val FALLBACK_PLANE_SPEED = 250.0 // 900 km/h
	}

	fun getDirection(request: DirectionRequest): DirectionResponse {
		val airDistance = request.startLocation.distanceTo(request.endLocation).roundToInt()
		val directions = mutableListOf<Direction>()

		if (airDistance <= PEDESTRIAN_MAX_LIMIT) {
			directions.add(getPedestrianFallbackDirection(airDistance))
		}
		if (airDistance <= CAR_MAX_LIMIT) {
			directions.add(getCarFallbackDirection(airDistance))
		}
		if (airDistance > PLANE_MIN_LIMIT) {
			directions.add(getPlaneDirection(airDistance))
		}

		return DirectionResponse(
			startLocation = request.startLocation,
			endLocation = request.endLocation,
			waypoints = request.waypoints,
			avoid = request.avoid,
			airDistance = airDistance,
			results = directions
		)
	}

	private fun getPedestrianFallbackDirection(distance: Int): Direction {
		val fallbackDistance = getPedestrianFallbackDistance(distance)
		return Direction(
			mode = DirectionMode.PEDESTRIAN,
			distance = fallbackDistance,
			duration = getPedestrianFallbackDuration(fallbackDistance),
			polyline = null,
			isEstimated = true
		)
	}

	private fun getCarFallbackDirection(distance: Int): Direction {
		val fallbackDistance = getCarFallbackDistance(distance)
		return Direction(
			mode = DirectionMode.CAR,
			distance = fallbackDistance,
			duration = getCarFallbackDuration(fallbackDistance),
			polyline = null,
			isEstimated = true
		)
	}

	private fun getPlaneDirection(distance: Int): Direction {
		return Direction(
			mode = DirectionMode.PLANE,
			distance = distance,
			duration = getPlaneFallbackDuration(distance),
			polyline = null,
			isEstimated = true
		)
	}

	private fun getPedestrianFallbackDistance(distance: Int): Int {
		return Math.round(when (distance) {
			in 0..2_000 -> distance * FALLBACK_DISTANCE_PEDESTRIAN_1
			in 2_000..6_000 -> distance * FALLBACK_DISTANCE_PEDESTRIAN_2
			else -> distance * FALLBACK_DISTANCE_PEDESTRIAN_3
		}).toInt()
	}

	private fun getCarFallbackDistance(distance: Int): Int {
		return Math.round(when (distance) {
			in 0..2_000 -> distance * FALLBACK_DISTANCE_CAR_1
			in 2_000..6_000 -> distance * FALLBACK_DISTANCE_CAR_2
			else -> distance * FALLBACK_DISTANCE_CAR_3
		}).toInt()
	}

	private fun getPedestrianFallbackDuration(distance: Int): Int {
		return Math.round(distance / FALLBACK_SPEED_PEDESTRIAN).toInt()
	}

	private fun getCarFallbackDuration(distance: Int): Int {
		return Math.round(when (distance) {
			in 0..20_000 -> distance / FALLBACK_SPEED_CAR_1
			in 20_000..40_000 -> distance / FALLBACK_CAR_SPEED_2
			else -> distance / FALLBACK_CAR_SPEED_3
		}).toInt()
	}

	private fun getPlaneFallbackDuration(distance: Int): Int {
		return Math.round(distance / FALLBACK_PLANE_SPEED).toInt() + 40 * 60
	}
}
