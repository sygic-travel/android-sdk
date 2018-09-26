package com.sygic.travel.sdk.trips.model

import com.sygic.travel.sdk.utils.DateTimeHelper
import org.junit.Test

class TripTest {
	@Test
	fun testStartsOnOk() {
		val trip = Trip("id")
		trip.startsOn = DateTimeHelper.dateToTimestamp("2018-02-02")!!
	}

	@Test(expected = IllegalArgumentException::class)
	fun testStarsOnWrong() {
		val trip = Trip("id")
		trip.startsOn = DateTimeHelper.datetimeToTimestamp("2018-02-02T01:00:00Z")!!
	}
}
