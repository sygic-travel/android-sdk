package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.DetailedPlace

@JsonClass(generateAdapter = true)
internal class ApiPlaceResponse(
	val place: ApiPlaceItemResponse
) {
	fun fromApi(): DetailedPlace {
		return place.fromApi()
	}
}
