package com.sygic.travel.sdk.trips.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TripTest {
	@Test
	fun withDaysTest() {
		val trip = Trip.createEmpty()

		val tripDays = trip.days
		var outsideDays: MutableList<TripDay>? = null

		val trip1 = trip.withDays { days ->
			outsideDays = days
			days.add(TripDay("a"))
			days.add(TripDay("b"))
			days
		}

		outsideDays!!.clear()

		assertTrue(
			trip.days.isEmpty()
		)
		assertTrue(
			tripDays.isEmpty()
		)

		assertEquals(
			listOf("a", "b"),
			trip1.days.map { it.note }
		)
	}

	@Test
	fun withItineraryTest() {
		val trip = Trip.createEmpty().copy(
			days = listOf(TripDay())
		)

		val tripDay = trip.days[0]
		val tripItinerary = trip.days[0].itinerary

		var outsideItinerary: MutableList<TripDayItem>? = null

		val trip1 = trip.withItinerary(0) { itinerary ->
			outsideItinerary = itinerary
			itinerary.add(TripDayItem("a"))
			itinerary.add(TripDayItem("b"))
			itinerary
		}

		outsideItinerary!!.clear()

		assertTrue(
			trip.days[0].itinerary.isEmpty()
		)
		assertTrue(
			tripDay.itinerary.isEmpty()
		)
		assertTrue(
			tripItinerary.isEmpty()
		)

		assertEquals(
			listOf("a", "b"),
			trip1.days[0].itinerary.map { it.placeId }
		)
	}

	@Test
	fun withItineraryFilterTest() {
		val trip = Trip.createEmpty().copy(
			days = listOf(TripDay(itinerary = listOf(
				TripDayItem("a"),
				TripDayItem("bb"),
				TripDayItem("1")
			)))
		)

		val tripDay = trip.days[0]
		val tripItinerary = trip.days[0].itinerary

		val trip1 = trip.withItinerary(0) { itinerary ->
			itinerary.filter { it.placeId.length < 2 }
		}

		assertEquals(
			3,
			trip.days[0].itinerary.size
		)
		assertEquals(
			3,
			tripDay.itinerary.size
		)
		assertEquals(
			3,
			tripItinerary.size
		)

		assertEquals(
			listOf("a", "1"),
			trip1.days[0].itinerary.map { it.placeId }
		)
	}

}
