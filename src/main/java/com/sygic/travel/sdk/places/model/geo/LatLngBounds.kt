package com.sygic.travel.sdk.places.model.geo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Suppress("unused")
@Parcelize
data class LatLngBounds(
	val northeast: LatLng,
	val southwest: LatLng
) : Parcelable, Serializable {
	/**
	 * Returns the center of the bounding box. The center is simply the average of the coordinates.
	 */
	fun getCenter(): LatLng {
		val lat = (southwest.lat + northeast.lat) / 2.0
		val lng = if (southwest.lng <= northeast.lng) {
			(southwest.lng + northeast.lng) / 2.0
		} else {
			(southwest.lng + northeast.lng + 360.0) / 2.0
		}
		return LatLng(lat, lng)
	}

	fun withPrecision(decimals: Int = 6): LatLngBounds {
		return copy(
			northeast = northeast.withPrecision(decimals),
			southwest = southwest.withPrecision(decimals)
		)
	}

	fun toApiExpression(): String {
		return "${southwest.toApiExpression()},${northeast.toApiExpression()}"
	}
}
