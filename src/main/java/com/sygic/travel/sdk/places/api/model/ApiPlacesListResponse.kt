package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.Place

internal class ApiPlacesListResponse(
	private val places: List<ApiPlaceListItemResponse>
) {
	fun getPlaces(): List<Place>? {
		return places.map { it.fromApi() }
	}
}
