package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.Place
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlacesListResponse(
	val places: List<ApiPlaceListItemResponse>
) {
	fun fromApi(): List<Place> {
		return places.map { it.fromApi() }
	}
}
