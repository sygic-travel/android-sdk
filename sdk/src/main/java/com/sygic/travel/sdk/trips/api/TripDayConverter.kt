package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.model.TripDayItem

class TripDayConverter constructor(
	private val tripDayItemConverter: TripDayItemConverter
) {
	fun fromApi(localDay: TripDay, apiDay: ApiTripItemResponse.Day) {
		localDay.note = apiDay.note
		val itemMaxIndex = localDay.itinerary.size - 1
		for ((i, item) in apiDay.itinerary.iterator().withIndex()) {
			val tripItem: TripDayItem
			if (itemMaxIndex < i) {
				tripItem = TripDayItem()
				tripItem.tripId = localDay.tripId
				tripItem.dayIndex = localDay.dayIndex
				localDay.itinerary.add(tripItem)
			} else {
				tripItem = localDay.itinerary[i]
			}
			tripDayItemConverter.fromApi(tripItem, item)
		}

		for (index in itemMaxIndex downTo apiDay.itinerary.size) {
			localDay.itinerary.removeAt(index)
		}
	}

	fun toApi(localDay: TripDay): ApiTripItemResponse.Day {
		return ApiTripItemResponse.Day(
			itinerary = localDay.itinerary.map { tripDayItemConverter.toApi(it) },
			note = localDay.note
		)
	}
}
