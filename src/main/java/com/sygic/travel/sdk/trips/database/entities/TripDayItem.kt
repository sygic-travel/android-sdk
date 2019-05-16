package com.sygic.travel.sdk.trips.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
	tableName = "trip_day_items",
	primaryKeys = [
		"trip_id",
		"day_index",
		"item_index"
	],
	foreignKeys = [
		ForeignKey(
			entity = Trip::class,
			parentColumns = ["id"],
			childColumns = ["trip_id"],
			onDelete = ForeignKey.CASCADE,
			onUpdate = ForeignKey.CASCADE
		)
	]
)
internal class TripDayItem {
	@ColumnInfo(name = "trip_id")
	lateinit var tripId: String

	@ColumnInfo(name = "day_index")
	var dayIndex: Int = 0

	@ColumnInfo(name = "item_index")
	var itemIndex: Int = 0

	@ColumnInfo
	lateinit var placeId: String

	@ColumnInfo
	var startTime: Int? = null

	@ColumnInfo
	var duration: Int? = null

	@ColumnInfo
	var note: String? = null

	@Embedded(prefix = "transport_")
	var transportFromPrevious: TripDayItemTransport? = null
}
