package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.DetailedPlace

@JsonClass(generateAdapter = true)
internal class ApiPlacesResponse(
	val places: List<ApiPlaceItemResponse>
) {
	fun fromApi(): List<DetailedPlace> {
		return places.map { it.fromApi() }
	}
}
