package com.sygic.travel.sdk.trips.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sygic.travel.sdk.trips.model.TripMedia
import com.sygic.travel.sdk.trips.model.TripPrivacyLevel
import com.sygic.travel.sdk.trips.model.TripPrivileges

@Entity(tableName = "trips")
internal class Trip {
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
	lateinit var privacyLevel: TripPrivacyLevel

	@Embedded
	lateinit var privileges: TripPrivileges

	@ColumnInfo(name = "starts_on")
	var startsOn: Long? = null

	@ColumnInfo(name = "days_count")
	var daysCount: Int = 0

	@Embedded
	var media: TripMedia? = null

	@ColumnInfo
	var destinations: ArrayList<String> = arrayListOf()

	@ColumnInfo(name = "is_changed")
	var isChanged: Boolean = false
}
