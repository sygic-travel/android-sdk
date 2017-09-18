package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.place.ApiPlaceDetail
import com.sygic.travel.sdk.model.place.Place

/**
 * Response that contains a list of places data. Suitable for showing places in a list or
 * spread on a map.
 */
internal class PlacesResponse : StResponse() {
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
		var apiPlaces: List<ApiPlaceDetail>? = null
	}
}
