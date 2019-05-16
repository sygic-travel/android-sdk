package com.sygic.travel.sdk.trips.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
	tableName = "trip_days",
	primaryKeys = ["trip_id", "day_index"],
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
internal class TripDay {
	@ColumnInfo(name = "trip_id")
	lateinit var tripId: String

	@ColumnInfo(name = "day_index")
	var dayIndex: Int = 0

	@ColumnInfo
	var note: String? = null
}
