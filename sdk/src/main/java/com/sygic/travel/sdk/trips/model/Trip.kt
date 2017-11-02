package com.sygic.travel.sdk.trips.model

import android.arch.persistence.room.Ignore

class Trip : TripInfo() {
	@Ignore
	var days: MutableList<TripDay> = arrayListOf()

	override var daysCount: Int
		get() = days.size
		set(value) {
			super.daysCount = value
		}

	fun reindexDays() {
		days.forEachIndexed { i, day ->
			day.itinerary.forEach {
				it.dayIndex = i
				it.tripId = id
			}
			day.dayIndex = i
			day.tripId = id
			day.trip = this
			day.reindexItinerary()
		}
		daysCount = days.size
	}

	fun getPlaceIds(): Set<String> {
		return days.map { it.getPlaceIds() }.flatten().toSet()
	}
}
