package com.sygic.travel.sdk.directions.facades

import android.os.Parcelable
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionMode
import com.sygic.travel.sdk.places.model.geo.LatLng
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class DirectionsQuery(
	var startLocation: LatLng,
	var endLocation: LatLng,
	var avoid: Set<DirectionAvoid> = emptySet(),
	var waypoints: List<LatLng> = emptyList(),
	var modes: Set<DirectionMode>? = null,
	var departAt: Date? = null,
	var arriveAt: Date? = null
) : Parcelable
