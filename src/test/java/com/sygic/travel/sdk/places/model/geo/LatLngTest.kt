package com.sygic.travel.sdk.places.model.geo

import com.sygic.travel.sdk.places.model.geo.LatLng
import org.junit.Assert.assertEquals
import org.junit.Test

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
}
