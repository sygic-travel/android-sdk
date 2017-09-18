package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.place.ApiTour
import com.sygic.travel.sdk.model.place.Tour

/**
 * Response that contains a list of Tour classes.
 */
internal class TourResponse : StResponse() {
	private var data: Data? = null

	fun getTours(): List<Tour>? {
		val apiTours = data?.apiTours
		val convertedTours: MutableList<Tour> = mutableListOf()

		apiTours?.mapTo(convertedTours) {
			it.convert()
		}

		return convertedTours
	}

	inner class Data {
		@SerializedName("tours")
		var apiTours: List<ApiTour>? = null
	}
}
