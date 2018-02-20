package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.DetailedPlace
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlaceResponse(
	val place: ApiPlaceItemResponse
) {
	fun fromApi(): DetailedPlace {
		return place.fromApi()
	}
}
