package com.sygic.travel.sdk.trips.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Ignore

@Entity(
	tableName = "trip_days",
	primaryKeys = arrayOf(
		"trip_id",
		"day_index"
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
class TripDay {
	@ColumnInfo(name = "trip_id")
	lateinit var tripId: String

	@ColumnInfo(name = "day_index")
	var dayIndex: Int = 0

	@ColumnInfo
	var note: String? = null

	// =============================================================================================

	@Ignore
	var itinerary: ArrayList<TripDayItem> = arrayListOf()

	@Ignore
	lateinit var trip: Trip

	fun reindexItinerary() {
		itinerary.forEachIndexed { i, item ->
			item.itemIndex = i
			item.dayIndex = dayIndex
			item.tripId = trip.id
		}
	}

	fun hasPlace(placeId: String): Boolean {
		return itinerary.find { it.placeId == placeId } != null
	}

	fun getPlaceIds(): Set<String> {
		return itinerary.map { it.placeId }.toSet()
	}
}
