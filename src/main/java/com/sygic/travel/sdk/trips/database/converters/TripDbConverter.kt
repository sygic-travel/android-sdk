package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripBase
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.database.entities.Trip as DbTrip
import com.sygic.travel.sdk.trips.database.entities.TripDay as DbTripDay
import com.sygic.travel.sdk.trips.database.entities.TripDayItem as DbTripDayItem

@Suppress("DEPRECATION")
internal class TripDbConverter constructor(
	private val tripDayDbConverter: TripDayDbConverter
) {
	fun fromAsTrip(dbTrip: DbTrip, dbDays: List<DbTripDay>, dbDayItems: Map<Int, List<DbTripDayItem>>): Trip {
		return Trip(
			id = dbTrip.id,
			name = dbTrip.name,
			startsOn = dbTrip.startsOn,
			privacyLevel = dbTrip.privacyLevel,
			url = dbTrip.url,
			privileges = dbTrip.privileges,
			isUserSubscribed = dbTrip.isUserSubscribed,
			isDeleted = dbTrip.isDeleted,
			media = dbTrip.media,
			updatedAt = dbTrip.updatedAt,
			isChanged = dbTrip.isChanged,
			ownerId = dbTrip.ownerId,
			version = dbTrip.version,
			destinations = dbTrip.destinations,
			days = dbDays.mapIndexed { index, dbDay ->
				tripDayDbConverter.from(dbDay, dbDayItems[index] ?: emptyList())
			}
		)
	}

	fun fromAsTripInfo(dbTrip: DbTrip): TripBase {
		return TripBase(
			id = dbTrip.id,
			name = dbTrip.name,
			startsOn = dbTrip.startsOn,
			privacyLevel = dbTrip.privacyLevel,
			url = dbTrip.url,
			privileges = dbTrip.privileges,
			isUserSubscribed = dbTrip.isUserSubscribed,
			isDeleted = dbTrip.isDeleted,
			media = dbTrip.media,
			updatedAt = dbTrip.updatedAt,
			isChanged = dbTrip.isChanged,
			ownerId = dbTrip.ownerId,
			version = dbTrip.version,
			daysCount = dbTrip.daysCount,
			destinations = dbTrip.destinations
		)
	}

	fun to(trip: TripInfo): DbTrip {
		val dbTrip = DbTrip()
		dbTrip.id = trip.id
		dbTrip.name = trip.name
		dbTrip.startsOn = trip.startsOn
		dbTrip.privacyLevel = trip.privacyLevel
		dbTrip.url = trip.url
		dbTrip.privileges = trip.privileges
		dbTrip.isUserSubscribed = trip.isUserSubscribed
		dbTrip.isDeleted = trip.isDeleted
		dbTrip.media = trip.media
		dbTrip.updatedAt = trip.updatedAt!!
		dbTrip.isChanged = trip.isChanged
		dbTrip.daysCount = trip.daysCount
		dbTrip.destinations = ArrayList(trip.destinations)
		dbTrip.ownerId = trip.ownerId
		dbTrip.version = trip.version
		return dbTrip
	}
}
