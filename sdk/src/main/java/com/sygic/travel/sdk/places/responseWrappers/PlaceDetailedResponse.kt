package com.sygic.travel.sdk.places.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.common.responseWrappers.StResponse
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.api.place.ApiPlaceDetail

/**
 * Response containing one detailed place data. Suitable for showing a place detail.
 */
class PlaceDetailedResponse : StResponse() {
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
