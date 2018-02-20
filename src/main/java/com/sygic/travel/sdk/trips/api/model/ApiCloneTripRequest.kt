package com.sygic.travel.sdk.trips.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiCloneTripRequest(
	val trip_id: String
)
