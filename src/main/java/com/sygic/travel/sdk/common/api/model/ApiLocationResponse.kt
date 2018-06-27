package com.sygic.travel.sdk.common.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.geo.Location

@JsonClass(generateAdapter = true)
internal class ApiLocationResponse(
	val lat: Float,
	val lng: Float
) {
	fun fromApi(): Location {
		return Location(lat, lng)
	}
}
