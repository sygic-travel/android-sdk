package com.sygic.travel.sdk.directions.helpers

import com.sygic.travel.sdk.directions.model.DirectionRequest
import com.sygic.travel.sdk.places.model.geo.LatLng

internal object AirDistanceCalculator {
	fun getAirDistance(location1: LatLng, location2: LatLng): Int {
		val results = floatArrayOf(0f, 0f, 0f)
		android.location.Location.distanceBetween(
			location1.lat,
			location1.lng,
			location2.lat,
			location2.lng,
			results
		)
		return Math.round(results[0])
	}

	fun getAirDistances(path: List<DirectionRequest>): List<Int> {
		return path.map { getAirDistance(it.startLocation, it.endLocation) }
	}
}
