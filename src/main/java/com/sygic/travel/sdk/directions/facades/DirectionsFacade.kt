package com.sygic.travel.sdk.directions.facades

import com.sygic.travel.sdk.directions.model.DirectionQuery
import com.sygic.travel.sdk.directions.model.DirectionResponse
import com.sygic.travel.sdk.directions.services.ApiDirectionsService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

/**
 * Directions facade calls the API for the real navigation polyline and time & distance calculations.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class DirectionsFacade internal constructor(
	private val directionsService: ApiDirectionsService
) {
	/**
	 * Queries the API for full directions. Reuses already cached directions.
	 */
	fun getDirection(request: DirectionQuery): DirectionResponse? {
		checkNotRunningOnMainThread()
		return directionsService.getDirections(listOf(request)).first()
	}

	/**
	 * Queries the API for full directions. Reuses already cached directions.
	 */
	fun getDirections(requests: List<DirectionQuery>): List<DirectionResponse?> {
		checkNotRunningOnMainThread()
		return directionsService.getDirections(requests)
	}
}
