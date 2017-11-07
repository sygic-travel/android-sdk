package com.sygic.travel.sdk.directions.model

import java.io.Serializable

class Directions(
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
