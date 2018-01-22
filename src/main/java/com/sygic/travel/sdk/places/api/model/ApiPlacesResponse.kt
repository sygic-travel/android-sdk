package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.Place

internal class ApiPlacesResponse(
	private val places: List<ApiPlaceItemResponse>
) {
	fun getPlaces(): List<Place>? {
		return places.map { it.fromApi() }
	}
}
