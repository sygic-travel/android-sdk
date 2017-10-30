package com.sygic.travel.sdk.places.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.api.place.ApiPlaceDetail

/**
 * ApiResponse that contains a list of places data. Suitable for showing places in a list or
 * spread on a map.
 */
class PlacesApiResponse : ApiResponse() {
	private var data: Data? = null

	fun getPlaces(): List<Place>? {
		val places = data?.apiPlaces
		val convertedPlaces: MutableList<Place> = mutableListOf()

		places?.mapTo(convertedPlaces) {
			it.convert()
		}

		return convertedPlaces
	}

	inner class Data {
		@SerializedName("places")
		internal var apiPlaces: List<ApiPlaceDetail>? = null
	}
}
