package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.DetailedPlace
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlacesResponse(
	val places: List<ApiPlaceItemResponse>
) {
	fun fromApi(): List<DetailedPlace> {
		return places.map { it.fromApi() }
	}
}
