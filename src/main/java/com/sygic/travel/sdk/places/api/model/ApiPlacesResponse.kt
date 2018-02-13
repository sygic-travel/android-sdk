package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.DetailedPlace

internal class ApiPlacesResponse(
	private val places: List<ApiPlaceItemResponse>
) {
	fun getPlaces(): List<DetailedPlace> {
		return places.map { it.fromApi() }
	}
}
