package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.places.model.geo.Location

data class TripItemTransportWaypoint(
	val placeId: String?,
	val location: Location
)
