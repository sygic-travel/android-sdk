package com.sygic.travel.sdk.directions.facades

import com.sygic.travel.sdk.directions.model.DirectionResponse
import com.sygic.travel.sdk.directions.model.DirectionRequest
import com.sygic.travel.sdk.directions.services.DirectionsService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

/**
 * Directions facade calculates estimated direction or calls the API for the real navigation polyline and time & distance calculations.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class DirectionsFacade internal constructor(
	private val directionsService: DirectionsService
) {
	/**
	 * Calculates directions fast & locally. Uses cached or air-distance estimated directions.
	 */
	fun getEstimatedDirections(requests: List<DirectionRequest>): List<DirectionResponse> {
		checkNotRunningOnMainThread()
		return directionsService.getSimpleDirections(requests)
	}

	/**
	 * Queries the API for full directions. Reuses already cached directions.
	 */
	fun getComplexDirections(requests: List<DirectionRequest>): List<DirectionResponse> {
		checkNotRunningOnMainThread()
		return directionsService.getComplexDirections(requests)
	}
}
