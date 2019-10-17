package com.sygic.travel.sdk.trips.model

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate

/**
 * Base trip entity representation.
 * It contains all trip metadata, does not contain days' definitions.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
data class TripBase internal constructor(
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
	override val daysCount: Int,
	override val destinations: List<String> = emptyList()
) : TripInfo
