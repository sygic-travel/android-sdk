package com.sygic.travel.sdk.geo.spread

import android.content.res.Resources
import android.graphics.Point
import com.sygic.travel.sdk.model.geo.Bounds
import com.sygic.travel.sdk.model.geo.Location
import com.sygic.travel.sdk.model.place.Place
import java.util.LinkedList

/**
 *
 * Performs the main spreading algorithm for places.
 */
class Spreader
/**
 *
 * Performs the main spreading algorithm for places.
 * @param resources Resources needed to get markers' dimensions.
 */
(private val resources: Resources) {

	/**
	 *
	 * Generates a list of spread places.
	 * @param places Places to spread.
	 * *
	 * @param bounds Map bounds withing which the places are supposed to be spread.
	 * *
	 * @param canvasSize Map canvas (view) size in pixels.
	 * *
	 * @return Spread places as [SpreadResult].
	 */
	fun spreadPlacesOnMap(
		places: List<Place>?,
		bounds: Bounds,
		canvasSize: CanvasSize
	): SpreadResult {
		val visiblePlaces = LinkedList<SpreadedPlace>()
		val hiddenPlaces = LinkedList<Place>()

		val sizeConfigs = SpreadConfigGenerator.getSpreadSizeConfigs(resources, bounds, canvasSize)

		for (place in places!!) {
			if (!place.hasLocation()) {
				hiddenPlaces.add(0, place)
				continue
			}

			assert(place.location != null)
			val canvasCoords = locationToCanvasCoords(place.location as Location, bounds, canvasSize)
			if (canvasCoords.x < 0 ||
				canvasCoords.y < 0 ||
				canvasCoords.x > canvasSize.width ||
				canvasCoords.y > canvasSize.height) {
				hiddenPlaces.add(0, place)
				continue
			}

			for (sizeConfig in sizeConfigs) {
				if (sizeConfig.isPhotoRequired && !place.hasThumbnailUrl()) {
					continue
				}
				if (sizeConfig.minimalRating > 0f && place.rating >= sizeConfig.minimalRating) {
					continue
				}
				if (!intersects(sizeConfig, canvasCoords, visiblePlaces)) {
					visiblePlaces.add(0, SpreadedPlace(place, canvasCoords, sizeConfig))
				}
			}
		}

		return SpreadResult(visiblePlaces, hiddenPlaces)
	}

	/**
	 *
	 * Determines a given size configuration intersects with any of already spreaded places.
	 * @param sizeConfig Size configuration of a place.
	 * *
	 * @param canvasCoords Place's coordinates on canvas.
	 * *
	 * @param spreadedPlaces Already spreaded places.
	 * *
	 * @return
	 * * **True** if place with given size configuration would intersect with any of already spreaded places.<br></br>
	 * * **False** otherwise - place can be displayed on a map with given {@param sizeConfig}
	 */
	private fun intersects(
		sizeConfig: SpreadSizeConfig,
		canvasCoords: Point,
		spreadedPlaces: List<SpreadedPlace>
	): Boolean {
		var intersects: Boolean
		var r2: Int
		val r = sizeConfig.radius + sizeConfig.margin

		for (spreadedPlace in spreadedPlaces) {
			val dX = canvasCoords.x - spreadedPlace.canvasCoords!!.x
			val dY = canvasCoords.y - spreadedPlace.canvasCoords!!.y

			r2 = spreadedPlace.sizeConfig!!.radius + spreadedPlace.sizeConfig!!.margin
			intersects = Math.pow(dX.toDouble(), 2.0) + Math.pow(dY.toDouble(), 2.0) <= Math.pow((r + r2).toDouble(), 2.0)
			if (intersects) {
				return true
			}
		}
		return false
	}

	/**
	 *
	 * Converts given location within given bounds to `x, y` coordinates within given canvas.
	 * @param location Location which is supposed be converted to `x, y` coordinates.
	 * *
	 * @param bounds Bounds the location lies within.
	 * *
	 * @param canvasSize Size of a canvas the coordinates are supposed to lie within.
	 * *
	 * @return [Point] on a canvas.
	 */
	private fun locationToCanvasCoords(
		location: Location,
		bounds: Bounds,
		canvasSize: CanvasSize
	): Point {
		val south: Double = bounds.south.toDouble()
		val west: Double = bounds.west.toDouble()
		val north: Double = bounds.north.toDouble()
		val east: Double = bounds.east.toDouble()

		val latDiff = north - location.lat
		var lngDiff = location.lng - west

		val latRatio = canvasSize.height / Math.abs(south - north)
		val lngRatio: Double

		if (west > east) { //date border
			lngRatio = canvasSize.width / Math.abs(180 - west + 180.0 + east)
			if (location.lng < 0 && location.lng < east) {
				lngDiff = 180 - west + 180.0 + location.lng.toDouble()
			}
			if (location.lng > 0 && location.lng < west) {
				lngDiff = 180 - west + 180.0 + location.lng.toDouble()
			}
		} else {
			lngRatio = canvasSize.width / Math.abs(west - east)
		}

		return Point(
			(lngDiff * lngRatio).toInt(),
			(latDiff * latRatio).toInt()
		)
	}
}
