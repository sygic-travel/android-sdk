package com.sygic.travel.sdk.trips.model

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate

interface TripInfo {
	companion object {
		internal const val LOCAL_ID_PREFIX = '*'
	}

	val id: String
	val name: String?
	val startsOn: LocalDate?
	val privacyLevel: TripPrivacyLevel
	val url: String
	val privileges: TripPrivileges
	val isUserSubscribed: Boolean
	val isDeleted: Boolean
	val media: TripMedia?
	val updatedAt: Instant?
	val isChanged: Boolean
	val ownerId: String?
	val version: Int
	val daysCount: Int

	@Deprecated("Destinations property is deprecated and not functional in the public SDK release.")
	val destinations: List<String>

	fun isLocal(): Boolean {
		return id.startsWith(LOCAL_ID_PREFIX)
	}
}
