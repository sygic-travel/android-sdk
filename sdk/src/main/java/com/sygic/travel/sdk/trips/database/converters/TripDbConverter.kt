package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.database.entities.Trip as DbTrip

@Suppress("DEPRECATION")
internal class TripDbConverter {
	fun fromAsTrip(dbTrip: DbTrip): Trip {
		val trip = Trip(dbTrip.id)
		from(dbTrip, trip)
		return trip
	}

	fun fromAsTripInfo(dbTrip: DbTrip): TripInfo {
		val trip = TripInfo(dbTrip.id)
		from(dbTrip, trip)
		return trip
	}

	private fun from(dbTrip: DbTrip, trip: TripInfo) {
		trip.name = dbTrip.name
		trip.startsOn = dbTrip.startsOn
		trip.privacyLevel = dbTrip.privacyLevel
		trip.url = dbTrip.url
		trip.isDeleted = dbTrip.isDeleted
		trip.media = dbTrip.media
		trip.updatedAt = dbTrip.updatedAt
		trip.isChanged = dbTrip.isChanged
		trip.daysCount = dbTrip.daysCount
		trip.destinations = dbTrip.destinations
		trip.ownerId = dbTrip.ownerId
		trip.version = dbTrip.version

		trip.privileges = dbTrip.privileges
	}

	fun to(trip: TripInfo): DbTrip {
		val dbTrip = DbTrip()
		dbTrip.id = trip.id
		dbTrip.name = trip.name
		dbTrip.startsOn = trip.startsOn
		dbTrip.privacyLevel = trip.privacyLevel
		dbTrip.url = trip.url
		dbTrip.privileges = trip.privileges
		dbTrip.isDeleted = trip.isDeleted
		dbTrip.media = trip.media
		dbTrip.updatedAt = trip.updatedAt
		dbTrip.isChanged = trip.isChanged
		dbTrip.daysCount = trip.daysCount
		dbTrip.destinations = trip.destinations
		dbTrip.ownerId = trip.ownerId
		dbTrip.version = trip.version
		return dbTrip
	}
}
