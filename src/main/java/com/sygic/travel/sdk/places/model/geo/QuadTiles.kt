package com.sygic.travel.sdk.places.model.geo

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.tan

fun LatLng.toQuadTile(zoom: Int): String {
	val xy = getXYFromLatLng(lat, lng, zoom)
	return toQuad(xy[0], xy[1], zoom)
}

fun LatLngBounds.toQuadTiles(zoom: Int): List<String> {
	val quadTiles = mutableListOf<String>()
	val nw = getXYFromLatLng(northeast.lat, southwest.lng, zoom)
	val ne = getXYFromLatLng(northeast.lat, northeast.lng, zoom)
	val sw = getXYFromLatLng(southwest.lat, southwest.lng, zoom)

	val xMin = nw[0]
	val xMax = ne[0]
	val yMin = nw[1]
	val yMax = sw[1]

	for (x in xMin..xMax) {
		for (y in yMin..yMax) {
			quadTiles.add(toQuad(x, y, zoom))
		}
	}

	return quadTiles
}

private fun getXYFromLatLng(lat: Double, lng: Double, zoom: Int): IntArray {
	val n = 2.0.pow(zoom.toDouble())
	val xTile = floor(n * ((lng + 180) / 360)).toInt()
	val yTile = floor(n * (1 - ln(tan(lat * PI / 180) + 1 / cos(lat * PI / 180)) / PI) / 2).toInt()
	return intArrayOf(xTile, yTile)
}

private fun toQuad(x: Int, y: Int, z: Int): String {
	var quadKey = ""
	for (i in z downTo 1) {
		val bitmask = 1 shl i - 1
		var digit = 0
		if (x and bitmask != 0) {
			digit = digit or 1
		}
		if (y and bitmask != 0) {
			digit = digit or 2
		}
		quadKey += digit
	}
	return quadKey
}
