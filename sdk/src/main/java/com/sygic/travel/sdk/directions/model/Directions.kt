package com.sygic.travel.sdk.directions.model

import java.io.Serializable

class Directions(
	val airDistance: Int,
	val car: List<Direction>,
	val pedestrian: List<Direction>,
	val plane: List<Direction>
) : Serializable {
	companion object {
		const val DEFAULT_MAX_PEDESTRIAN = 2_000
		const val DEFAULT_MAX_CAR = 1_000_000
	}

	fun getDefault(preferredMode: DirectionMode? = null): Direction {
		return when {
			((airDistance <= DEFAULT_MAX_PEDESTRIAN && preferredMode == null) || preferredMode == DirectionMode.PEDESTRIAN) && pedestrian.isNotEmpty() -> pedestrian.first()
			((airDistance in DEFAULT_MAX_PEDESTRIAN..DEFAULT_MAX_CAR && preferredMode == null) || preferredMode == DirectionMode.CAR) && car.isNotEmpty() -> car.first()
			((airDistance > DEFAULT_MAX_CAR && preferredMode == null) || preferredMode == DirectionMode.PLANE) && plane.isNotEmpty() -> plane.first()
			pedestrian.isNotEmpty() -> pedestrian.first()
			car.isNotEmpty() -> car.first()
			plane.isNotEmpty() -> plane.first()
			else -> throw IllegalStateException()
		}
	}
}

