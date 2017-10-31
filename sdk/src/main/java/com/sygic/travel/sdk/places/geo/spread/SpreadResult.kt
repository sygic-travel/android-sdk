package com.sygic.travel.sdk.places.geo.spread

import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.PlaceInfo
import java.util.LinkedList

/**
 *
 * Output of the [Spreader.spreadPlacesOnMap] spread algorithm.
 */
class SpreadResult
/**
 *
 * Outupt of the spread algorithm.
 * @param visiblePlaces Places visible on map.
 * *
 * @param hiddenPlaces Places, which are not visible.
 */
(var visiblePlaces: LinkedList<SpreadedPlace>?, var hiddenPlaces: LinkedList<PlaceInfo>?)
