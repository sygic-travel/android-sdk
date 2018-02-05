package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.utils.DateTimeHelper
import org.junit.Test
import java.util.Date

class TripTest {
	@Test
	fun testStartsOnOk() {
		val trip = Trip("id")
		trip.startsOn = Date(DateTimeHelper.dateToTimestamp("2018-02-02")!! * 1000)
	}

	@Test(expected = IllegalArgumentException::class)
	fun testStarsOnWrong() {
		val trip = Trip("id")
		trip.startsOn = Date(DateTimeHelper.datetimeToTimestamp("2018-02-02T01:00:00Z")!! * 1000)
	}
}
