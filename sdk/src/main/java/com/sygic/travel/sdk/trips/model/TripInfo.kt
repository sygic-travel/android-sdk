package com.sygic.travel.sdk.trips.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "trips")
open class TripInfo {
	@PrimaryKey
	lateinit var id: String

	@ColumnInfo(name = "owner_id")
	var ownerId: String? = null

	@ColumnInfo(name = "name")
	var name: String? = ""

	@ColumnInfo
	var version: Int = 0

	@ColumnInfo
	var url: String = ""

	@ColumnInfo(name = "updated_at")
	var updatedAt: Long = 0

	@ColumnInfo(name = "is_deleted")
	var isDeleted: Boolean = false

	@ColumnInfo(name = "privacy_level")
	var privacyLevel: PrivacyLevel = PrivacyLevel.PUBLIC

	@Embedded
	var privileges: Privileges = Privileges(edit = true, delete = true, manage = true)

	@ColumnInfo(name = "starts_on")
	var startsOn: Long? = null

	@ColumnInfo(name = "days_count")
	open var daysCount: Int = 0

	@Embedded
	var media: TripMedia? = null

	@ColumnInfo
	var destinations: ArrayList<String> = arrayListOf()

	@ColumnInfo(name = "is_changed")
	var isChanged: Boolean = false

	// =============================================================================================

	companion object {
		const val LOCAL_ID_PREFIX = '*'
	}

	enum class PrivacyLevel {
		PRIVATE,
		SHAREABLE,
		PUBLIC,
	}

	class Privileges(
		val edit: Boolean,
		val manage: Boolean,
		val delete: Boolean
	)

	val isLocal: Boolean
		get() {
			return id.startsWith(LOCAL_ID_PREFIX)
		}
}
