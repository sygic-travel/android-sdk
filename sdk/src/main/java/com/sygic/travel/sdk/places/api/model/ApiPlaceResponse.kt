package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.Place

/**
 * ApiResponse that contains a list of places data. Suitable for showing places in a list or
 * spread on a map.
 */
class ApiPlaceResponse(
	private val place: ApiPlaceItemResponse
) {
	fun getPlace(): Place {
		return place.fromApi()
	}
}
