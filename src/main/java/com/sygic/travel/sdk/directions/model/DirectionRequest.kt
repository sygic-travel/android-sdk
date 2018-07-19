package com.sygic.travel.sdk.directions.model

import com.sygic.travel.sdk.places.model.geo.LatLng

data class DirectionRequest(
	val startLocation: LatLng,
	val endLocation: LatLng,
	val waypoints: List<LatLng>,
	val avoid: List<DirectionAvoid>
)
