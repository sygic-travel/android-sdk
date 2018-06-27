package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.Place

@JsonClass(generateAdapter = true)
internal class ApiPlacesListResponse(
	val places: List<ApiPlaceListItemResponse>
) {
	fun fromApi(): List<Place> {
		return places.map { it.fromApi() }
	}
}
