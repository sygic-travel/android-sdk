package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.geo.Bounds

@JsonClass(generateAdapter = true)
internal class ApiBoundsResponse(
	val north: Float,
	val east: Float,
	val south: Float,
	val west: Float
) {
	fun fromApi(): Bounds {
		return Bounds(
			north = north,
			east = east,
			south = south,
			west = west
		)
	}
}
