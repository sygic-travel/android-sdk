package com.sygic.travel.sdk.places.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.api.place.ApiPlaceDetail

/**
 * ApiResponse containing one detailed place data. Suitable for showing a place detail.
 */
class PlaceDetailedApiResponse : ApiResponse() {
	private val data: Data? = null

	fun getPlace(): Place? {
		val placeDetail = data?.apiPlace
		val place = placeDetail?.convert()

		return place
	}

	inner class Data {
		@SerializedName("place")
		internal var apiPlace: ApiPlaceDetail? = null
	}
}
