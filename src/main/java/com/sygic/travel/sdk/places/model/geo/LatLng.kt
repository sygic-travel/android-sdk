package com.sygic.travel.sdk.places.model.geo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.math.RoundingMode
import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sqrt

@Parcelize
data class LatLng(
	val lat: Double,
	val lng: Double
) : Parcelable, Serializable {
	/**
	 * Returns the approximate shortest distance to the point in meters. Uses Haversine_formula.
	 */
	fun distanceTo(point: LatLng): Double {
		// see https://stackoverflow.com/a/21623206/859688
		//     https://en.wikipedia.org/wiki/Haversine_formula
		val lat1 = lat
		val lon1 = lng
		val lat2 = point.lat
		val lon2 = point.lng
		val p = PI / 180
		val a = 0.5 - cos((lat2 - lat1) * p) / 2 + cos(lat1 * p) * cos(lat2 * p) * (1 - cos((lon2 - lon1) * p)) / 2
		return 12742 * asin(sqrt(a))
	}

	fun withPrecision(decimals: Int = 6): LatLng {
		return copy(
			lat = lat.toBigDecimal().setScale(decimals, RoundingMode.HALF_DOWN).toDouble(),
			lng = lng.toBigDecimal().setScale(decimals, RoundingMode.HALF_DOWN).toDouble()
		)
	}

	fun toApiExpression(): String {
		return "$lat,$lng"
	}
}
