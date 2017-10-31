package com.sygic.travel.sdk.directions.services

import android.content.Context
import com.sygic.travel.sdk.directions.helpers.CachingHelper
import com.sygic.travel.sdk.directions.model.Directions
import com.sygic.travel.sdk.directions.model.DirectionsRequest
import java.io.File
import java.util.Locale

class CacheService constructor(
	context: Context
) {
	private val cache = CachingHelper<Directions>(File(context.cacheDir.path + File.separator + "sygic-travel-directions"))

	fun getCachedDirections(requests: List<DirectionsRequest>): List<Directions?> {
		return requests.map { getCachedDirections(it) }
	}

	fun storeDirections(requests: List<DirectionsRequest>, allDirections: List<Directions?>) {
		allDirections.forEachIndexed { i, directions ->
			if (directions != null) {
				val key = getCacheKey(requests[i])
				cache.put(key, directions)
			}
		}
	}

	private fun getCachedDirections(request: DirectionsRequest): Directions? {
		return cache.get(getCacheKey(request))
	}

	private fun getCacheKey(request: DirectionsRequest): String {
		return "%.6f-%.6f-%.6f-%.6f".format(
			Locale.ENGLISH,
			request.from.lat,
			request.from.lng,
			request.to.lat,
			request.to.lng
		).replace('.', '_')
	}
}
