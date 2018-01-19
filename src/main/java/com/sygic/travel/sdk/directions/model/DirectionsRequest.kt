package com.sygic.travel.sdk.directions.model

import com.sygic.travel.sdk.places.model.geo.Location

data class DirectionsRequest(
	val from: Location,
	val to: Location,
	val waypoints: List<Location>,
	val avoid: List<DirectionAvoid>
)
