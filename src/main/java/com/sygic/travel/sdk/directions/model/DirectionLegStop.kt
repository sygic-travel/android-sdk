package com.sygic.travel.sdk.directions.model

import com.sygic.travel.sdk.places.model.geo.LatLng
import java.io.Serializable

@Suppress("unused")
class DirectionLegStop(
	val name: String?,
	val location: LatLng,
	val arrivalAt: DirectionTime?,
	val departureAt: DirectionTime?,
	val plannedArrivalAt: DirectionTime?,
	val plannedDepartureAt: DirectionTime?
) : Serializable
