package com.sygic.travel.sdk.trips.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiTripsListResponse(
	val trips: List<ApiTripListItemResponse>
)
