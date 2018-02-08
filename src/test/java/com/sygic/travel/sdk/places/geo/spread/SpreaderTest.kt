package com.sygic.travel.sdk.places.geo.spread

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.mock
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.geo.Bounds
import com.sygic.travel.sdk.places.model.geo.Location
import org.junit.Assert.assertTrue
import org.junit.Test

class SpreaderTest {

	@Test
	fun testLocationToCanvasCoords() {
		// places init
		val places = ArrayList<Place>()

		// this place will be evaluated as hidden because location has (0,0) coordinates
		val place1 = Place()
		val location1 = Location(0f, 0f)
		place1.location = location1
		places.add(place1)

		// visible place
		val place2 = Place()
		val location2 = Location(48.3333f, 17.3333f)
		place2.location = location2
		places.add(place2)

		// this place will intersects, so that it won't be neither visible nor hidden
		val place3 = Place()
		val location3 = Location(48.4444f, 17.4444f)
		place3.location = location3
		places.add(place3)

		// configuration
		val resource = mock<Resources> { }
		val testedObject = Spreader(resource)

		val canvasSize = CanvasSize(740, 320)

		val bounds = Bounds(
			east = 48f,
			west = 49f,
			north = 17f,
			south = 16f
		)

		// calling tested method
		val spreadPlacesOnMap = testedObject.spreadPlacesOnMap(places, bounds, canvasSize)

		val visiblePlaces = spreadPlacesOnMap.visiblePlaces
		val hiddenPlaces = spreadPlacesOnMap.hiddenPlaces

		// verifying
		assertTrue(visiblePlaces?.size == 1)
		assertTrue(hiddenPlaces?.size == 1)
	}
}
