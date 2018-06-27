package com.sygic.travel.sdk.directions.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.directions.model.DirectionMode

@JsonClass(generateAdapter = true)
internal class ApiDirectionsResponse(
	val path: List<Directions>
) {
	@JsonClass(generateAdapter = true)
	internal class Directions(
		val directions: List<Direction>
	) {
		@JsonClass(generateAdapter = true)
		internal class Direction(
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

			@JsonClass(generateAdapter = true)
			internal class Location(
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
