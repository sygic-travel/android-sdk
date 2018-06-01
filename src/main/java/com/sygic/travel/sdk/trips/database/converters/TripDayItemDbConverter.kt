package com.sygic.travel.sdk.trips.database.converters

import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.model.TripDayItem
import com.sygic.travel.sdk.trips.database.entities.TripDayItem as DbTripDayItem

internal class TripDayItemDbConverter(
	private val transportDbConverter: TripDayItemTransportDbConverter
) {
	fun from(dbItem: DbTripDayItem): TripDayItem {
		return TripDayItem(
			placeId = dbItem.placeId,
			startTime = dbItem.startTime,
			duration = dbItem.duration,
			note = dbItem.note,
			transportFromPrevious = transportDbConverter.from(dbItem.transportFromPrevious)
		)
	}

	fun to(item: TripDayItem, trip: Trip, tripDay: TripDay): DbTripDayItem {
		val dbItem = DbTripDayItem()
		dbItem.tripId = trip.id
		dbItem.dayIndex = trip.days.indexOf(tripDay)
		dbItem.itemIndex = tripDay.itinerary.indexOf(item)
		dbItem.placeId = item.placeId
		dbItem.startTime = item.startTime
		dbItem.duration = item.duration
		dbItem.note = item.note
		dbItem.transportFromPrevious = transportDbConverter.to(item.transportFromPrevious)
		return dbItem
	}
}
