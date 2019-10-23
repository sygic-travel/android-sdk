package com.sygic.travel.sdk.places.model.geo

import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import org.junit.Assert.assertEquals
import org.junit.Test

class LatLngBoundsTest {
	@Test
	fun testRounding() {
		val latLngBounds = LatLngBounds(
			northeast = LatLng(50.0786358, 14.4593813),
			southwest = LatLng(49.9760764, 14.2444614)
		)

		assertEquals(
			"49.9760764,14.2444614,50.0786358,14.4593813",
			latLngBounds.toApiExpression()
		)

		assertEquals(
			"49.97608,14.24446,50.07864,14.45938",
			latLngBounds.withPrecision(5).toApiExpression()
		)

		assertEquals(
			"49.9760764,14.2444614,50.0786358,14.4593813",
			latLngBounds.withPrecision(10).toApiExpression()
		)
	}
}
