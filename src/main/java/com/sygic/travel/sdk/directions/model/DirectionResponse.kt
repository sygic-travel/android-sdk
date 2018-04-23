package com.sygic.travel.sdk.directions.model

import com.sygic.travel.sdk.places.model.geo.Location
import java.io.Serializable

class DirectionResponse constructor(
	val startLocation: Location,
	val endLocation: Location,
	val waypoints: List<Location>,
	val avoid: List<DirectionAvoid>,
	val airDistance: Int,
	val car: List<Direction>,
	val pedestrian: List<Direction>,
	val plane: List<Direction>
) : Serializable {
	fun getByMode(mode: DirectionMode?): Direction? {
		return when (mode) {
			DirectionMode.CAR -> car.firstOrNull()
			DirectionMode.PEDESTRIAN -> pedestrian.firstOrNull()
			DirectionMode.PLANE -> plane.firstOrNull()
			else -> null
		}
	}
}
