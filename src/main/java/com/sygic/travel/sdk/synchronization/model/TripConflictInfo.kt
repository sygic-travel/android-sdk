package com.sygic.travel.sdk.synchronization.model

import com.sygic.travel.sdk.trips.model.Trip
import org.threeten.bp.Instant

data class TripConflictInfo constructor(
	val localTrip: Trip,
	val remoteTrip: Trip,
	val remoteTripUserName: String?,
	val remoteTripUpdatedAt: Instant
)
