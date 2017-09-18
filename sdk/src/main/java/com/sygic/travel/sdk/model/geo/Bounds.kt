package com.sygic.travel.sdk.model.geo

/**
 * Geographical bounds - south, west, north, east.
 */
class Bounds {
	var north: Float = 0.toFloat()
	var east: Float = 0.toFloat()
	var south: Float = 0.toFloat()
	var west: Float = 0.toFloat()

	fun toQueryString(): String {
		return south.toString() + "," + west + "," + north + "," + east
	}
}
