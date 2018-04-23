package com.sygic.travel.sdk.directions.model

import com.sygic.travel.sdk.places.model.geo.Location

data class DirectionRequest(
	val startLocation: Location,
	val endLocation: Location,
	val waypoints: List<Location>,
	val avoid: List<DirectionAvoid>
)
