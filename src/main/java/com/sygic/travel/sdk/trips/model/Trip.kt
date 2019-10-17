package com.sygic.travel.sdk.trips.model

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import java.util.UUID

/**
 * Trip entity representation.
 * It contains all metadata and days' definitions.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
data class Trip constructor(
	override val id: String,
	override val name: String?,
	override val startsOn: LocalDate?,
	override val privacyLevel: TripPrivacyLevel,
	override val url: String,
	override val privileges: TripPrivileges,
	override val isUserSubscribed: Boolean,
	override val isDeleted: Boolean,
	override val media: TripMedia?,
	override val updatedAt: Instant?,
	override val isChanged: Boolean,
	override val ownerId: String?,
	override val version: Int,
	override val destinations: List<String>,
	val days: List<TripDay>
) : TripInfo {
	override var daysCount: Int = 0
		get() = days.size
		internal set

	fun getPlaceIds(): Set<String> {
		return days.map { it.getPlaceIds() }.flatten().toSet()
	}

	internal fun getLocalPlaceIds(): List<String> {
		return getPlaceIds().filter { it.startsWith("*") }
	}

	companion object {
		/**
		 * Creates a new trip instance with a local ID.
		 */
		fun createEmpty(): Trip {
			return Trip(
				id = TripInfo.LOCAL_ID_PREFIX + UUID.randomUUID().toString(),
				name = "",
				startsOn = null,
				privacyLevel = TripPrivacyLevel.PRIVATE,
				url = "",
				privileges = TripPrivileges(edit = true, delete = true, manage = true),
				isUserSubscribed = false,
				isDeleted = false,
				media = null,
				updatedAt = null,
				isChanged = false,
				ownerId = null,
				version = 0,
				destinations = emptyList(),
				days = emptyList()
			)
		}
	}
}
