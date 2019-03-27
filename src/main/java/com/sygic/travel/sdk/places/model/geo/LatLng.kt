package com.sygic.travel.sdk.places.model.geo

import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class LatLng(
	val lat: Double,
	val lng: Double
) : Parcelable, Serializable {
	/**
	 * Returns the approximate shortest distance to the point in meters. Uses WGS84 ellipsoid.
	 */
	fun distanceTo(point: LatLng): Float {
		val results = floatArrayOf(0f, 0f, 0f)
		Location.distanceBetween(
			lat,
			lng,
			point.lat,
			point.lng,
			results
		)
		return results[0]
	}

	internal fun toApiQuery(): String {
		return "$lat,$lng"
	}
}
