package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.api.model.ApiTripListItemResponse
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripDay
import org.junit.Assert.assertSame
import org.junit.Test

class TripConverterTest {
	@Test
	fun fromApi() {
		val converter = TripConverter(TripDayConverter(TripDayItemConverter(TripItemTransportConverter())))

		val day1 = TripDay()
		val day2 = TripDay()
		val day3 = TripDay()

		val localTrip = Trip()
		localTrip.id = "ab123"
		localTrip.days.addAll(arrayListOf(day1, day2, day3))

		val apiTrip = ApiTripItemResponse(
			id = "ab123",
			owner_id = "yxcv",
			name = "Trip To Somewhere",
			version = 1,
			url = "http://travel.sygic.com",
			updated_at = "2017-01-01T00:00:00Z",
			is_deleted = false,
			privacy_level = ApiTripListItemResponse.PRIVACY_PRIVATE,
			privileges = ApiTripListItemResponse.Privileges(true, true, true),
			starts_on = null,
			ends_on = null,
			days_count = 3,
			media = null,
			days = arrayListOf(
				ApiTripItemResponse.Day(arrayListOf(), "note1"),
				ApiTripItemResponse.Day(arrayListOf(), "note2")
			),
			destinations = arrayListOf()
		)

		converter.fromApi(localTrip, apiTrip)

		assertSame(2, localTrip.days.size)
	}
}
