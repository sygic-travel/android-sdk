package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.model.TripDayItem

internal class TripDayItemConverter constructor(
	private val tripItemTransportConverter: TripItemTransportConverter
) {
	fun fromApi(apiItem: ApiTripItemResponse.Day.DayItem): TripDayItem {
		val localItem = TripDayItem(apiItem.place_id)
		localItem.startTime = apiItem.start_time
		localItem.duration = apiItem.duration
		localItem.note = apiItem.note
		localItem.transportFromPrevious = tripItemTransportConverter.fromApi(apiItem.transport_from_previous)
		return localItem
	}

	fun toApi(localItem: TripDayItem): ApiTripItemResponse.Day.DayItem {
		return ApiTripItemResponse.Day.DayItem(
			place_id = localItem.placeId,
			start_time = localItem.startTime,
			duration = localItem.duration,
			note = localItem.note,
			transport_from_previous = tripItemTransportConverter.toApi(localItem.transportFromPrevious)
		)
	}
}
