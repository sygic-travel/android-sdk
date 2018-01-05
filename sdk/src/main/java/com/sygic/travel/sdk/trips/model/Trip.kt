package com.sygic.travel.sdk.trips.model

import java.util.UUID

/**
 * Trip entity representation.
 * It contains all metadata and days' definitions.
 */
@Suppress("ConvertSecondaryConstructorToPrimary")
class Trip internal constructor(id: String) : TripInfo(id) {
	companion object {
		/**
		 * Creates a new trip instance with a local ID.
		 */
		fun create(): Trip {
			return Trip(UUID.randomUUID().toString())
		}

		/**
		 * Creates a new trip with preloaded internal properties.
		 * This method is internal but open.
		 */
		fun createWithInternalProperties(
			tripId: String,
			url: String,
			privileges: TripPrivileges,
			isDeleted: Boolean,
			media: TripMedia?,
			updatedAt: Long,
			isChanged: Boolean,
			daysCount: Int,
			ownerId: String?,
			version: Int
		): Trip {
			val trip = Trip(tripId)
			trip.url = url
			trip.privileges = privileges
			trip.isDeleted = isDeleted
			trip.media = media
			trip.updatedAt = updatedAt
			trip.isChanged = isChanged
			trip.daysCount = daysCount
			trip.ownerId = ownerId
			trip.version = version
			return trip
		}
	}

	var days = mutableListOf<TripDay>()
	override var daysCount: Int = 0
		get() = days.size
		internal set

	fun getPlaceIds(): Set<String> {
		return days.map { it.getPlaceIds() }.flatten().toSet()
	}
}
