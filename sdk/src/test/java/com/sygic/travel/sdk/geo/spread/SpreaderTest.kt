package com.sygic.travel.sdk.geo.spread

import android.content.res.Resources
import com.nhaarman.mockito_kotlin.mock
import com.sygic.travel.sdk.model.geo.Bounds
import com.sygic.travel.sdk.model.geo.Location
import com.sygic.travel.sdk.model.place.Place
import org.junit.Assert.assertTrue
import org.junit.Test

class SpreaderTest {

    @Test
    fun testLocationToCanvasCoords() {
        // places init
        val places = ArrayList<Place>()

        // this place will be evaluated as hidden because location has (0,0) coordinates
        val place1 = Place()
        val location1 = Location()
        location1.lat = 0.toFloat()
        location1.lng = 0.toFloat()
        place1.location = location1
        places.add(place1)

        // visible place
        val place2 = Place()
        val location2 = Location()
        location2.lat = 48.3333.toFloat()
        location2.lng = 17.3333.toFloat()
        place2.location = location2
        places.add(place2)

        // this place will intersects, so that it won't be neither visible nor hidden
        val place3 = Place()
        val location3 = Location()
        location3.lat = 48.4444.toFloat()
        location3.lng = 17.4444.toFloat()
        place3.location = location3
        places.add(place3)

        // configuration
        val resource = mock<Resources> { }
        val testedObject = Spreader(resource)

        val canvasSize = CanvasSize(740, 320)

        val bounds = Bounds()
        bounds.east = 48.toFloat()
        bounds.west = 49.toFloat()
        bounds.north = 17.toFloat()
        bounds.south = 16.toFloat()

        // calling tested method
        val spreadPlacesOnMap = testedObject.spreadPlacesOnMap(places, bounds, canvasSize)

        val visiblePlaces = spreadPlacesOnMap.visiblePlaces
        val hiddenPlaces = spreadPlacesOnMap.hiddenPlaces

        // verifying
        assertTrue(visiblePlaces?.size == 1)
        assertTrue(hiddenPlaces?.size == 1)
    }
}
