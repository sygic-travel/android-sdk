package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.DetailedPlace

internal class ApiPlaceResponse(
	private val place: ApiPlaceItemResponse
) {
	fun getPlace(): DetailedPlace {
		return place.fromApi()
	}
}
