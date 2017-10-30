package com.sygic.travel.sdk.tours.responseWrapper

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.tours.model.Tour
import com.sygic.travel.sdk.tours.model.api.ApiTour

/**
 * ApiResponse that contains a list of Tour classes.
 */
class TourApiResponse : ApiResponse() {
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
