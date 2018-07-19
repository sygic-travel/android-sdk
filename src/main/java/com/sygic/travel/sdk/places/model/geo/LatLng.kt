package com.sygic.travel.sdk.places.model.geo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class LatLng(
	val lat: Double,
	val lng: Double
) : Parcelable, Serializable
