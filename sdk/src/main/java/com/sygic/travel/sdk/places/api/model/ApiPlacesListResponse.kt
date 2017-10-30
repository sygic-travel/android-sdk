package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.PlaceInfo

/**
 * ApiResponse that contains a list of places data. Suitable for showing places in a list or
 * spread on a map.
 */
class ApiPlacesListResponse(
	private val places: List<ApiPlaceListItemResponse>
) {
	fun getPlaces(): List<PlaceInfo>? {
		return places.map { it.fromApi() }
	}
}
