package com.sygic.travel.sdk.places.geo.spread

import android.content.res.Resources
import android.graphics.Point
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.places.model.geo.LatLngBounds
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
	 * @param bounds Map bounding_box withing which the places are supposed to be spread.
	 * *
	 * @param canvasSize Map canvas (view) size in pixels.
	 * *
	 * @return Spread places as [SpreadResult].
	 */
	fun spreadPlacesOnMap(
		places: List<Place>?,
		bounds: LatLngBounds,
		canvasSize: CanvasSize
	): SpreadResult {
		val visiblePlaces = LinkedList<SpreadedPlace>()
		val hiddenPlaces = LinkedList<Place>()

		val sizeConfigs = SpreadConfigGenerator.getSpreadSizeConfigs(resources, bounds, canvasSize)

		for (place in places!!) {
			if (place.location.lat == 0.0 && place.location.lng == 0.0) {
				hiddenPlaces.add(0, place)
				continue
			}

			val canvasCoords = locationToCanvasCoords(place.location, bounds, canvasSize)
			if (canvasCoords.x < 0 ||
				canvasCoords.y < 0 ||
				canvasCoords.x > canvasSize.width ||
				canvasCoords.y > canvasSize.height) {
				hiddenPlaces.add(0, place)
				continue
			}

			for (sizeConfig in sizeConfigs) {
				if (sizeConfig.isPhotoRequired && place.thumbnailUrl != null) {
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
	 * Converts given location within given bounding_box to `x, y` coordinates within given canvas.
	 * @param location Location which is supposed be converted to `x, y` coordinates.
	 * *
	 * @param bounds Bounds the location lies within.
	 * *
	 * @param canvasSize Size of a canvas the coordinates are supposed to lie within.
	 * *
	 * @return [Point] on a canvas.
	 */
	private fun locationToCanvasCoords(
		location: LatLng,
		bounds: LatLngBounds,
		canvasSize: CanvasSize
	): Point {
		val south = bounds.southwest.lat
		val west = bounds.southwest.lng
		val north = bounds.northeast.lat
		val east = bounds.northeast.lng

		val latDiff = north - location.lat
		var lngDiff = location.lng - west

		val latRatio = canvasSize.height / Math.abs(south - north)
		val lngRatio: Double

		if (west > east) { //date border
			lngRatio = canvasSize.width / Math.abs(180 - west + 180.0 + east)
			if (location.lng < 0 && location.lng < east) {
				lngDiff = 180 - west + 180.0 + location.lng
			}
			if (location.lng > 0 && location.lng < west) {
				lngDiff = 180 - west + 180.0 + location.lng
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
