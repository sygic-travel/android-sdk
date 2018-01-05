package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.database.entities.Trip as DbTrip

@Suppress("DEPRECATION")
internal class TripDbConverter {
	fun from(dbTrip: DbTrip): Trip {
		val trip = Trip()
		trip.id = dbTrip.id
		trip.name = dbTrip.name
		trip.startsOn = dbTrip.startsOn
		trip.privacyLevel = dbTrip.privacyLevel
		trip.url = dbTrip.url
		trip.privileges = dbTrip.privileges
		trip.isDeleted = dbTrip.isDeleted
		trip.media = dbTrip.media
		trip.updatedAt = dbTrip.updatedAt
		trip.isChanged = dbTrip.isChanged
		trip.daysCount = dbTrip.daysCount
		trip.destinations = dbTrip.destinations
		trip.ownerId = dbTrip.ownerId
		trip.version = dbTrip.version
		return trip
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
