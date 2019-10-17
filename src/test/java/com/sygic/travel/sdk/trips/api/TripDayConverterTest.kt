package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import org.junit.Assert.assertSame
import org.junit.Test

class TripDayConverterTest {
	@Test
	fun fromApi() {
		val converter = TripDayConverter(TripDayItemConverter(TripItemTransportConverter()))

		val apiDay = ApiTripItemResponse.Day(listOf(
			ApiTripItemResponse.Day.DayItem("poi:540", null, null, null, null),
			ApiTripItemResponse.Day.DayItem("poi:531", null, null, null, null)
		), "new note")

		val localDay = converter.fromApi(apiDay)

		assertSame("new note", localDay.note)
		assertSame(2, localDay.itinerary.size)
		assertSame("poi:540", localDay.itinerary[0].placeId)
		assertSame("poi:531", localDay.itinerary[1].placeId)
	}
}
