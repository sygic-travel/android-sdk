package com.sygic.travel.sdk.places.model.geo

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class QuadTilesKtTest {
	@Test
	fun testLatLng() {
		assertEquals(
			"",
			LatLng(0.0, 0.0).toQuadTile(0)
		)

		assertEquals(
			"1202123331232",
			LatLng(49.1565342, 16.6278505).toQuadTile(13)
		)
	}

	@Test
	fun testLatLngBounds() {
		assertArrayEquals(
			arrayOf(""),
			LatLngBounds(LatLng(0.0, 0.0), LatLng(0.0, 0.0)).toQuadTiles(0).toTypedArray()
		)

		assertArrayEquals(
			arrayOf("1202123331232", "1202123331233"),
			LatLngBounds(LatLng(49.1702934, 16.6859227), LatLng(49.1565342, 16.6278505)).toQuadTiles(13).toTypedArray()
		)
	}
}
