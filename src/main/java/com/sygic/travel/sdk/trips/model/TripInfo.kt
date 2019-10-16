package com.sygic.travel.sdk.trips.model

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate

/**
 * Basic trip entity representation.
 * It contains all trip metadata, does not contain days' definitions.
 */
open class TripInfo internal constructor(id: String) {
	companion object {
		internal const val LOCAL_ID_PREFIX = '*'
	}

	var id: String = id
		internal set
	var name: String? = ""
	var startsOn: LocalDate? = null
	var privacyLevel: TripPrivacyLevel = TripPrivacyLevel.PRIVATE
		set(value) {
			if (privileges.manage) {
				field = value
			} else {
				throw IllegalStateException("You cannot change the trip's privacyLevel property without the manage privilege.")
			}
		}
	var url: String = ""
		internal set
	var privileges: TripPrivileges = TripPrivileges(edit = true, delete = true, manage = true)
		internal set
	var isUserSubscribed: Boolean = false
		internal set
	var isDeleted: Boolean = false
		set(value) {
			if (privileges.delete) {
				field = value
			} else {
				throw IllegalStateException("You cannot change the trip's isDeleted property without the delete privilege.")
			}
		}
	var media: TripMedia? = null
		internal set
	var updatedAt: Instant? = null
		internal set
	var isChanged: Boolean = false
		internal set
	open var daysCount: Int = 0
		internal set
	@Deprecated("Destinations property is deprecated and not functional in the public SDK release.")
	var destinations: ArrayList<String> = arrayListOf()
		get() {
			return ArrayList(field.filter { it.isNotBlank() })
		}
		set(value) {
			field = ArrayList(value.filter { it.isNotBlank() })
		}
	var ownerId: String? = null
		internal set
	var version: Int = 0
		internal set

	fun isLocal(): Boolean {
		return id.startsWith(LOCAL_ID_PREFIX)
	}
}
