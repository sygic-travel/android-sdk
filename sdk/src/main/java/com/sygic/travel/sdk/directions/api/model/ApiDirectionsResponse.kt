package com.sygic.travel.sdk.directions.api.model

import com.sygic.travel.sdk.directions.model.DirectionMode

internal class ApiDirectionsResponse(
	val path: List<Directions>
) {
	class Directions(
		val directions: List<Direction>
	) {
		class Direction(
			val distance: Int,
			val duration: Int,
			val polyline: String,
			val mode: String,
			val start_location: Location,
			val end_location: Location
		) {
			companion object {
				const val MODE_CAR = "car"
				const val MODE_PEDESTRIAN = "pedestrian"
			}

			class Location(
				val lat: Float,
				val lng: Float
			)

			val enumMode: DirectionMode?
				get() {
					return when (mode) {
						MODE_CAR -> DirectionMode.CAR
						MODE_PEDESTRIAN -> DirectionMode.PEDESTRIAN
						else -> null
					}
				}
		}
	}
}
