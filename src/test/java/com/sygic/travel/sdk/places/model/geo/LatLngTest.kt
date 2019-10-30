package com.sygic.travel.sdk.places.model.geo

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.roundToInt

class LatLngTest {
	@Test
	fun testRounding() {
		val latLng = LatLng(50.0786358, 14.4593813)

		assertEquals(
			"50.0786358,14.4593813",
			latLng.toApiExpression()
		)

		assertEquals(
			"50.07864,14.45938",
			latLng.withPrecision(5).toApiExpression()
		)

		assertEquals(
			"50.0786358,14.4593813",
			latLng.withPrecision(10).toApiExpression()
		)
	}

	@Test
	fun testDistance() {
		val from = LatLng(49.233461, 16.572517)
		val to = LatLng(49.231109, 16.573447)

		assertEquals(270, from.distanceTo(to).roundToInt())
	}
}
