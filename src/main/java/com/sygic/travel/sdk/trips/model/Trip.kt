package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.utils.asDate
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
			return Trip(TripInfo.LOCAL_ID_PREFIX + UUID.randomUUID().toString())
		}

		/**
		 * INTERNAL
		 * Creates a trip with passed properties.
		 */
		fun createFrom(
			id: String,
			name: String?,
			startsOn: Long?,
			privacyLevel: TripPrivacyLevel,
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
			val trip = Trip(id)
			trip.name = name
			trip.startsOn = startsOn?.asDate()
			trip.privacyLevel = privacyLevel
			trip.url = url
			trip.isDeleted = isDeleted
			trip.media = media
			trip.updatedAt = updatedAt.asDate()
			trip.isChanged = isChanged
			trip.daysCount = daysCount
			trip.ownerId = ownerId
			trip.version = version

			trip.privileges = privileges
			return trip
		}
	}

	var days = listOf<TripDay>()
	override var daysCount: Int = 0
		get() = days.size
		internal set

	fun getPlaceIds(): Set<String> {
		return days.map { it.getPlaceIds() }.flatten().toSet()
	}
}
