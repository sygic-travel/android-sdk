package com.sygic.travel.sdk.common.api.model

import com.sygic.travel.sdk.places.model.geo.Location
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiLocationResponse(
	val lat: Float,
	val lng: Float
) {
	fun fromApi(): Location {
		return Location(lat, lng)
	}
}
