package com.sygic.travel.sdk.common.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.geo.LatLng

@JsonClass(generateAdapter = true)
internal class ApiLocationResponse(
	val lat: Double,
	val lng: Double
) {
	fun fromApi(): LatLng {
		return LatLng(lat, lng)
	}
}
