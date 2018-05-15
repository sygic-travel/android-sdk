package com.sygic.travel.sdk.places.geo.spread

import android.content.res.Resources
import com.sygic.travel.sdk.R
import com.sygic.travel.sdk.places.model.Level
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.geo.Bounds
import com.sygic.travel.sdk.places.model.geo.Location
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test

class SpreaderTest {

	@Test
	fun testLocationToCanvasCoords() {
		// places init
		val places = mutableListOf<Place>()

		// this place will be evaluated as hidden because location has (0,0) coordinates
		places.add(createPlace(Location(0f, 0f)))

		// visible place
		places.add(createPlace(Location(48.3333f, 17.3333f)))

		// this place will intersects, so that it won't be neither visible nor hidden
		places.add(createPlace(Location(48.4444f, 17.4444f)))

		// configuration
		val resource = mockk<Resources>()
		every { resource.getDimensionPixelSize(R.dimen.marker_radius_popular) } returns 40
		every { resource.getDimensionPixelSize(R.dimen.marker_margin_popular) } returns 30
		every { resource.getDimensionPixelSize(R.dimen.marker_radius_big) } returns 27
		every { resource.getDimensionPixelSize(R.dimen.marker_margin_big) } returns 22
		every { resource.getDimensionPixelSize(R.dimen.marker_radius_medium) } returns 18
		every { resource.getDimensionPixelSize(R.dimen.marker_margin_medium) } returns 15
		every { resource.getDimensionPixelSize(R.dimen.marker_radius_small) } returns 9
		every { resource.getDimensionPixelSize(R.dimen.marker_margin_small) } returns 7
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

	private fun createPlace(location: Location): Place {
		return Place(
			id = "id",
			boundingBox = null,
			categories = emptySet(),
			name = "",
			url = null,
			location = location,
			level = Level.POI,
			marker = "marker",
			nameSuffix = null,
			parentIds = emptySet(),
			perex = null,
			quadkey = "1",
			rating = 0f,
			thumbnailUrl = null,
			starRating = null,
			starRatingUnofficial = null,
			customerRating = null,
			ownerId = null
		)
	}
}
