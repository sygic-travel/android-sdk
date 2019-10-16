package com.sygic.travel.sdk.trips.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sygic.travel.sdk.trips.model.TripMedia
import com.sygic.travel.sdk.trips.model.TripPrivacyLevel
import com.sygic.travel.sdk.trips.model.TripPrivileges
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate

@Entity(tableName = "trips")
internal class Trip {
	@PrimaryKey
	lateinit var id: String

	@ColumnInfo(name = "owner_id")
	var ownerId: String? = null

	@ColumnInfo(name = "is_user_subscribed")
	var isUserSubscribed: Boolean = true

	@ColumnInfo(name = "name")
	var name: String? = ""

	@ColumnInfo
	var version: Int = 0

	@ColumnInfo
	var url: String = ""

	@ColumnInfo(name = "updated_at")
	var updatedAt: Instant = Instant.now()

	@ColumnInfo(name = "is_deleted")
	var isDeleted: Boolean = false

	@ColumnInfo(name = "privacy_level")
	lateinit var privacyLevel: TripPrivacyLevel

	@Embedded
	lateinit var privileges: TripPrivileges

	@ColumnInfo(name = "starts_on")
	var startsOn: LocalDate? = null

	@ColumnInfo(name = "days_count")
	var daysCount: Int = 0

	@Embedded
	var media: TripMedia? = null

	@ColumnInfo
	var destinations: ArrayList<String> = arrayListOf()

	@ColumnInfo(name = "is_changed")
	var isChanged: Boolean = false
}
