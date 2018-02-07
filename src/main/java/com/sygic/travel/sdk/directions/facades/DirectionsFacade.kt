package com.sygic.travel.sdk.directions.facades

import com.sygic.travel.sdk.directions.model.Directions
import com.sygic.travel.sdk.directions.model.DirectionsRequest
import com.sygic.travel.sdk.directions.services.DirectionsService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

/**
 * Directions facade calculates estimated direction or calls the API for the real navigation polyline and time & distance calculations.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class DirectionsFacade internal constructor(
	private val directionsService: DirectionsService
) {
	fun getSimpleDirections(requests: List<DirectionsRequest>): List<Directions> {
		checkNotRunningOnMainThread()
		return directionsService.getSimpleDirections(requests)
	}

	fun getComplexDirections(requests: List<DirectionsRequest>): List<Directions> {
		checkNotRunningOnMainThread()
		return directionsService.getComplexDirections(requests)
	}
}
