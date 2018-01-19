package com.sygic.travel.sdk.directions.helpers

import com.sygic.travel.sdk.directions.model.DirectionsRequest
import com.sygic.travel.sdk.places.model.geo.Location

internal object AirDistanceCalculator {
	fun getAirDistance(location1: Location, location2: Location): Int {
		val results = floatArrayOf(0f, 0f, 0f)
		android.location.Location.distanceBetween(
			location1.lat.toDouble(),
			location1.lng.toDouble(),
			location2.lat.toDouble(),
			location2.lng.toDouble(),
			results
		)
		return Math.round(results[0])
	}

	fun getAirDistances(path: List<DirectionsRequest>): List<Int> {
		return path.map { getAirDistance(it.from, it.to) }
	}
}
