package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.TripDayItem
import com.sygic.travel.sdk.trips.database.entities.TripDayItem as DbTripDayItem

internal class TripDayItemDbConverter(
	private val transportDbConverter: TripDayItemTransportDbConverter
) {
	fun from(dbItem: DbTripDayItem): TripDayItem {
		val item = TripDayItem(dbItem.placeId)
		item.startTime = dbItem.startTime
		item.duration = dbItem.duration
		item.note = dbItem.note
		item.transportFromPrevious = transportDbConverter.from(dbItem.transportFromPrevious)
		return item
	}

	fun to(item: TripDayItem, tripId: String, dayIndex: Int, itemIndex: Int): DbTripDayItem {
		val dbItem = DbTripDayItem()
		dbItem.tripId = tripId
		dbItem.dayIndex = dayIndex
		dbItem.itemIndex = itemIndex
		dbItem.placeId = item.placeId
		dbItem.startTime = item.startTime
		dbItem.duration = item.duration
		dbItem.note = item.note
		dbItem.transportFromPrevious = transportDbConverter.to(item.transportFromPrevious)
		return dbItem
	}
}
