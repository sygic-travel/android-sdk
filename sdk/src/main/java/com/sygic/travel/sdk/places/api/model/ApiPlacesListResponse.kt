package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.PlaceInfo

internal class ApiPlacesListResponse(
	private val places: List<ApiPlaceListItemResponse>
) {
	fun getPlaces(): List<PlaceInfo>? {
		return places.map { it.fromApi() }
	}
}
