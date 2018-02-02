package com.sygic.travel.sdk.synchronization.model

import com.sygic.travel.sdk.trips.model.Trip
import java.util.Date

data class TripConflictInfo(
	val localTrip: Trip,
	val remoteTrip: Trip,
	val remoteTripUserName: String,
	val remoteTripUpdatedAt: Date
)
