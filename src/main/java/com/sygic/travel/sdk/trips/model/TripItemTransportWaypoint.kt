package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.places.model.geo.LatLng

data class TripItemTransportWaypoint(
	val placeId: String?,
	val location: LatLng
)
