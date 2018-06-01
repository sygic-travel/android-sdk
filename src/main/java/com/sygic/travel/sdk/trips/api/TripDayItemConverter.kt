package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.model.TripDayItem

internal class TripDayItemConverter constructor(
	private val tripItemTransportConverter: TripItemTransportConverter
) {
	fun fromApi(apiItem: ApiTripItemResponse.Day.DayItem): TripDayItem {
		return TripDayItem(
			placeId = apiItem.place_id,
			startTime = apiItem.start_time,
			duration = apiItem.duration,
			note = apiItem.note,
			transportFromPrevious = tripItemTransportConverter.fromApi(apiItem.transport_from_previous)
		)
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
