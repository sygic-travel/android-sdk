package com.sygic.travel.sdk.common.api.model

import com.sygic.travel.sdk.places.model.geo.Location

internal class ApiLocationResponse(
	val lat: Float,
	val lng: Float
) {
	fun fromApi(): Location {
		val location = Location()
		location.lat = lat
		location.lng = lng
		return location
	}
}
