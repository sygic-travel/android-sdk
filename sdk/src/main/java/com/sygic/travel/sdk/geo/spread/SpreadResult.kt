package com.sygic.travel.sdk.geo.spread

import com.sygic.travel.sdk.model.place.Place
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
(var visiblePlaces: LinkedList<SpreadedPlace>?, var hiddenPlaces: LinkedList<Place>?)