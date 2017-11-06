package com.sygic.travel.sdk.directions.services

import com.sygic.travel.sdk.directions.helpers.AirDistanceCalculator
import com.sygic.travel.sdk.directions.model.Direction
import com.sygic.travel.sdk.directions.model.DirectionMode
import com.sygic.travel.sdk.directions.model.Directions
import com.sygic.travel.sdk.directions.model.DirectionsRequest

class NaiveDirectionsService {
	companion object {
		const val FALLBACK_DISTANCE_PEDESTRIAN_1 = 1.35
		const val FALLBACK_DISTANCE_PEDESTRIAN_2 = 1.22
		const val FALLBACK_DISTANCE_PEDESTRIAN_3 = 1.106
		const val FALLBACK_DISTANCE_CAR_1 = 1.8
		const val FALLBACK_DISTANCE_CAR_2 = 1.6
		const val FALLBACK_DISTANCE_CAR_3 = 1.2
		const val FALLBACK_SPEED_PEDESTRIAN = 1.3333 // 4.8 km/h
		const val FALLBACK_SPEED_CAR_1 = 7.5 // 27 km/h
		const val FALLBACK_CAR_SPEED_2 = 15.0 // 54 km/h
		const val FALLBACK_CAR_SPEED_3 = 25.0 // 90 km/h
		const val FALLBACK_PLANE_SPEED = 250.0 // 900 km/h
	}

	fun getDirection(request: DirectionsRequest): Directions {
		val airDistance = AirDistanceCalculator.getAirDistance(request.from, request.to)
		val pedestrian = arrayListOf<Direction>()
		val car = arrayListOf<Direction>()
		val plane = arrayListOf<Direction>()

		if (airDistance > 50_000) {
			plane.add(getPlaneDirection(airDistance))
		}
		if (airDistance <= 50_000) {
			pedestrian.add(getPedestrianFallbackDirection(airDistance))
		}
		if (airDistance <= 2_000_000) {
			car.add(getCarFallbackDirection(airDistance))
		}

		return Directions(
			airDistance = airDistance,
			pedestrian = pedestrian,
			car = car,
			plane = plane
		)
	}

	fun getDirections(requests: List<DirectionsRequest>): List<Directions> {
		return requests.map { getDirection(it) }
	}

	fun getPedestrianFallbackDirection(distance: Int): Direction {
		val fallbackDistance = getPedestrianFallbackDistance(distance)
		return Direction(
			mode = DirectionMode.PEDESTRIAN,
			distance = fallbackDistance,
			duration = getPedestrianFallbackDuration(fallbackDistance),
			polyline = null,
			isEstimated = true
		)
	}

	fun getCarFallbackDirection(distance: Int): Direction {
		val fallbackDistance = getCarFallbackDistance(distance)
		return Direction(
			mode = DirectionMode.CAR,
			distance = fallbackDistance,
			duration = getCarFallbackDuration(fallbackDistance),
			polyline = null,
			isEstimated = true
		)
	}

	fun getPlaneDirection(distance: Int): Direction {
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
