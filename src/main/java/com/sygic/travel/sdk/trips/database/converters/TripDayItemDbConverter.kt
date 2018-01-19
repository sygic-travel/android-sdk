package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.model.TripDayItem
import com.sygic.travel.sdk.trips.database.entities.TripDayItem as DbTripDayItem

internal class TripDayItemDbConverter(
	private val transportDbConverter: TripDayItemTransportDbConverter
) {
	fun from(dbItem: DbTripDayItem, tripDay: TripDay): TripDayItem {
		val item = TripDayItem(dbItem.placeId, tripDay)
		item.startTime = dbItem.startTime
		item.duration = dbItem.duration
		item.note = dbItem.note
		item.transportFromPrevious = transportDbConverter.from(dbItem.transportFromPrevious)
		return item
	}

	fun to(item: TripDayItem): DbTripDayItem {
		val dbItem = DbTripDayItem()
		dbItem.tripId = item.tripDay.trip.id
		dbItem.dayIndex = item.tripDay.getDayIndex()
		dbItem.itemIndex = item.getItemIndex()
		dbItem.placeId = item.placeId
		dbItem.startTime = item.startTime
		dbItem.duration = item.duration
		dbItem.note = item.note
		dbItem.transportFromPrevious = transportDbConverter.to(item.transportFromPrevious)
		return dbItem
	}
}
