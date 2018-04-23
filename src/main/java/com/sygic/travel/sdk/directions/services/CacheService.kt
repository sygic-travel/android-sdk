package com.sygic.travel.sdk.directions.services

import android.content.Context
import com.sygic.travel.sdk.directions.helpers.CachingHelper
import com.sygic.travel.sdk.directions.model.DirectionResponse
import com.sygic.travel.sdk.directions.model.DirectionRequest
import java.io.File

internal class CacheService constructor(
	context: Context
) {
	private val cache = CachingHelper<DirectionResponse>(File(context.cacheDir.path + File.separator + "sygic-travel-directions"))

	fun getCachedDirections(requests: List<DirectionRequest>): List<DirectionResponse?> {
		return requests.map { getCachedDirections(it) }
	}

	fun storeDirections(requests: List<DirectionRequest>, allDirections: List<DirectionResponse?>) {
		allDirections.forEachIndexed { i, directions ->
			if (directions != null) {
				val key = getCacheKey(requests[i])
				cache.put(key, directions)
			}
		}
	}

	private fun getCachedDirections(request: DirectionRequest): DirectionResponse? {
		return cache.get(getCacheKey(request))
	}

	private fun getCacheKey(request: DirectionRequest): String {
		return request.hashCode().toString()
	}
}
