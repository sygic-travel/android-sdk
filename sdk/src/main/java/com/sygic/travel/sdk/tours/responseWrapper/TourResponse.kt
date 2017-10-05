package com.sygic.travel.sdk.tours.responseWrapper

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.common.responseWrappers.StResponse
import com.sygic.travel.sdk.tours.model.Tour
import com.sygic.travel.sdk.tours.model.api.ApiTour

/**
 * Response that contains a list of Tour classes.
 */
class TourResponse : StResponse() {
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
		internal var apiTours: List<ApiTour>? = null
	}
}
