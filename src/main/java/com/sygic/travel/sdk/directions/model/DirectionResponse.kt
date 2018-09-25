package com.sygic.travel.sdk.directions.model

import com.sygic.travel.sdk.places.model.geo.LatLng
import java.io.Serializable

@Suppress("unused")
class DirectionResponse constructor(
	val startLocation: LatLng,
	val endLocation: LatLng,
	val waypoints: List<LatLng>,
	val avoid: Set<DirectionAvoid>,
	val airDistance: Int,
	val directions: List<Direction>
) : Serializable
