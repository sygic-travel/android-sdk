package com.sygic.travel.sdk.trips.model

data class TripPrivileges constructor(
	val edit: Boolean,
	val manage: Boolean,
	val delete: Boolean
)
