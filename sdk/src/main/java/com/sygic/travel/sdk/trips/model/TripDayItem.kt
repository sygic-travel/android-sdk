package com.sygic.travel.sdk.trips.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(
	tableName = "trip_day_items",
	primaryKeys = arrayOf(
		"trip_id",
		"day_index",
		"item_index"
	),
	foreignKeys = arrayOf(
		ForeignKey(
			entity = TripInfo::class,
			parentColumns = arrayOf("id"),
			childColumns = arrayOf("trip_id"),
			onDelete = ForeignKey.CASCADE,
			onUpdate = ForeignKey.CASCADE
		)
	)
)
class TripDayItem {
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
	var transportFromPrevious: TripItemTransport? = null
}
