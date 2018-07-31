package com.sygic.travel.sdk.places.model.geo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class LatLngBounds(
	val northeast: LatLng,
	val southwest: LatLng
) : Parcelable, Serializable {
	internal fun toApiQuery(): String {
		return "${southwest.lng},${southwest.lat},${northeast.lng},${northeast.lat}"
	}
}
