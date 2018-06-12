package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.model.Trip
import org.junit.Assert.assertEquals
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

		val localTrip = Trip("123")
		val localDay = converter.fromApi(apiDay, localTrip)
		localTrip.days = listOf(localDay)

		assertSame("new note", localDay.note)
		assertSame(2, localDay.itinerary.size)
		assertSame("poi:540", localDay.itinerary[0].placeId)
		assertSame("poi:531", localDay.itinerary[1].placeId)

		val localDay2 = converter.fromApi(apiDay, localTrip)
		assertEquals(localDay, localDay2)
		localTrip.days = localTrip.days + localDay2
		assertSame(0, localTrip.days.indexOf(localDay2))
	}
}
