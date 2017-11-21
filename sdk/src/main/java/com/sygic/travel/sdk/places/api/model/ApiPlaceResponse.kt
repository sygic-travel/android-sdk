package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.Place

internal class ApiPlaceResponse(
	private val place: ApiPlaceItemResponse
) {
	fun getPlace(): Place {
		return place.fromApi()
	}
}
