package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.model.TripDayItem
import org.junit.Assert.assertSame
import org.junit.Test

class TripDayConverterTest {
	@Test
	fun fromApi() {
		val converter = TripDayConverter(TripDayItemConverter(TripItemTransportConverter()))

		val item1 = TripDayItem()
		item1.placeId = "poi:530"
		val item2 = TripDayItem()
		item2.placeId = "poi:540"
		val item3 = TripDayItem()
		item3.placeId = "poi:531"

		val localDay = TripDay()
		localDay.itinerary.addAll(arrayListOf(item1, item2, item3))
		val apiDay = ApiTripItemResponse.Day(arrayListOf(
			ApiTripItemResponse.Day.DayItem("poi:540", null, null, null, null),
			ApiTripItemResponse.Day.DayItem("poi:531", null, null, null, null)
		), "new note")

		converter.fromApi(localDay, apiDay)

		assertSame("new note", localDay.note)
		assertSame(2, localDay.itinerary.size)
		assertSame("poi:540", localDay.itinerary[0].placeId)
		assertSame("poi:531", localDay.itinerary[1].placeId)
	}
}
