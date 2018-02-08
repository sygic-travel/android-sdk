package com.sygic.travel.sdk.places.model.geo

/**
 * Geographical bounding_box - south, west, north, east.
 */
class Bounds {
	var north: Float = 0.toFloat()
	var east: Float = 0.toFloat()
	var south: Float = 0.toFloat()
	var west: Float = 0.toFloat()

	internal fun toApiQueryString(): String {
		return south.toString() + "," + west + "," + north + "," + east
	}
}
