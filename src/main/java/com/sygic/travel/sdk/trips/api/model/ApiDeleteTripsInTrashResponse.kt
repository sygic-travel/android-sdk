package com.sygic.travel.sdk.trips.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class ApiDeleteTripsInTrashResponse(
	val deleted_trip_ids: List<String>
)
