package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.places.model.geo.LatLngBounds

@JsonClass(generateAdapter = true)
internal class ApiBoundsResponse(
	val north: Double,
	val east: Double,
	val south: Double,
	val west: Double
) {
	fun fromApi(): LatLngBounds {
		return LatLngBounds(
			LatLng(north, east),
			LatLng(south, west)
		)
	}
}
