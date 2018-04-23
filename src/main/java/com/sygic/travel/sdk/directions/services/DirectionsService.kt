package com.sygic.travel.sdk.directions.services

import com.sygic.travel.sdk.directions.model.DirectionRequest
import com.sygic.travel.sdk.directions.model.DirectionResponse

internal class DirectionsService constructor(
	private val apiDirectionsService: ApiDirectionsService,
	private val estimatedDirectionsService: EstimatedDirectionsService,
	private val cacheService: CacheService
) {
	fun getEstimatedDirection(request: DirectionRequest): DirectionResponse {
		return estimatedDirectionsService.getDirection(request)
	}

	fun getEstimatedDirections(requests: List<DirectionRequest>): List<DirectionResponse> {
		return requests.map { estimatedDirectionsService.getDirection(it) }
	}

	fun getComplexDirections(requests: List<DirectionRequest>): List<DirectionResponse> {
		val cachedDirections = cacheService.getCachedDirections(requests)
		val missingRequests = requests.filterIndexed { i, _ -> cachedDirections[i] == null }

		val apiDirections = ArrayList(apiDirectionsService.getDirections(missingRequests))
		cacheService.storeDirections(missingRequests, apiDirections)

		return cachedDirections.mapIndexed { i, directions ->
			return@mapIndexed directions ?: apiDirections.removeAt(0) ?: estimatedDirectionsService.getDirection(requests[i])
		}
	}
}
