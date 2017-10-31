package com.sygic.travel.sdk.directions.facades

import com.sygic.travel.sdk.Callback
import com.sygic.travel.sdk.directions.model.Directions
import com.sygic.travel.sdk.directions.model.DirectionsRequest
import com.sygic.travel.sdk.directions.services.DirectionsService
import com.sygic.travel.sdk.utils.runAsync
import com.sygic.travel.sdk.utils.runWithCallback
import kotlinx.coroutines.experimental.runBlocking

class DirectionsFacade constructor(
	private val directionsService: DirectionsService
) {
	fun getSimpleDirections(requests: List<DirectionsRequest>): List<Directions> {
		return runBlocking { directionsService.getSimpleDirections(requests) }
	}

	fun getComplexDirections(requests: List<DirectionsRequest>, callback: Callback<List<Directions>>) {
		runWithCallback({ directionsService.getComplexDirections(requests) }, callback)
	}

	suspend fun getComplexDirections(requests: List<DirectionsRequest>): List<Directions> {
		return runAsync { directionsService.getComplexDirections(requests) }
	}
}
