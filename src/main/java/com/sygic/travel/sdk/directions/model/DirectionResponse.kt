package com.sygic.travel.sdk.directions.model

import com.sygic.travel.sdk.places.model.geo.LatLng
import java.io.Serializable

class DirectionResponse constructor(
	val startLocation: LatLng,
	val endLocation: LatLng,
	val waypoints: List<LatLng>,
	val avoid: List<DirectionAvoid>,
	val airDistance: Int,
	val results: List<Direction>
) : Serializable {
	fun getByMode(mode: DirectionMode?): Direction? {
		return results.firstOrNull { it.mode == mode }
	}
}
