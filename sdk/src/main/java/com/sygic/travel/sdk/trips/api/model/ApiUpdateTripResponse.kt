package com.sygic.travel.sdk.trips.api.model

internal class ApiUpdateTripResponse(
	val trip: ApiTripItemResponse,
	val conflictResolution: String?,
	val conflictInfo: ConflictInfo?
) {
	companion object {
		const val CONFLICT_RESOLUTION_IGNORED = "ignored"
	}

	class ConflictInfo(
		val last_user_name: String,
		val last_updated_at: String
	)
}
