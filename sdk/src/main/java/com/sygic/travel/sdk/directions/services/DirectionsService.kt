package com.sygic.travel.sdk.directions.services

import com.sygic.travel.sdk.directions.model.Directions
import com.sygic.travel.sdk.directions.model.DirectionsRequest

class DirectionsService constructor(
	private val apiDirectionsService: ApiDirectionsService,
	private val naiveDirectionsService: NaiveDirectionsService,
	private val cacheService: CacheService
) {
	fun getSimpleDirections(requests: List<DirectionsRequest>): List<Directions> {
		val cachedDirections = cacheService.getCachedDirections(requests)
		return cachedDirections.mapIndexed { i, directions ->
			return@mapIndexed directions ?: naiveDirectionsService.getDirection(requests[i])
		}
	}

	suspend fun getComplexDirections(requests: List<DirectionsRequest>): List<Directions> {
		val cachedDirections = cacheService.getCachedDirections(requests)
		val missingRequests = requests.filterIndexed { i, _ ->
			return@filterIndexed cachedDirections[i] == null
		}

		val apiDirections = ArrayList(apiDirectionsService.getDirections(missingRequests))
		cacheService.storeDirections(missingRequests, apiDirections)

		return cachedDirections.mapIndexed { i, directions ->
			return@mapIndexed directions ?: apiDirections.removeAt(0) ?: naiveDirectionsService.getDirection(requests[i])
		}
	}
}
