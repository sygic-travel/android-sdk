package com.sygic.travel.sdk.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class DateTimeHelperTest {
	@Test
	fun testParsingDateTime() {
		assertEquals(
			1509622833L,
			DateTimeHelper.datetimeToTimestamp("2017-11-02T11:40:33Z")
		)

		assertEquals(
			1509622833L,
			DateTimeHelper.datetimeToTimestamp("2017-11-02T12:40:33+0100")
		)

		assertEquals(
			1509622833L,
			DateTimeHelper.datetimeToTimestamp("2017-11-02T12:40:33+01:00")
		)

		assertEquals(
			null,
			DateTimeHelper.datetimeToTimestamp(null)
		)
	}

	@Test
	fun testParsingDate() {
		assertEquals(
			1509580800L,
			DateTimeHelper.dateToTimestamp("2017-11-02")
		)

		assertEquals(
			null,
			DateTimeHelper.dateToTimestamp(null)
		)
	}

	@Test
	fun testFormatDateTime() {
		assertEquals(
			"2017-11-02T11:40:33Z",
			DateTimeHelper.timestampToDatetime(1509622833L)
		)

		assertEquals(
			null,
			DateTimeHelper.timestampToDatetime(null)
		)
	}

	@Test
	fun testFormatDate() {
		assertEquals(
			"2017-11-02",
			DateTimeHelper.timestampToDate(1509622833L)
		)

		assertEquals(
			"2017-11-02",
			DateTimeHelper.timestampToDate(1509580800L)
		)

		assertEquals(
			null,
			DateTimeHelper.timestampToDate(null)
		)
	}
}
