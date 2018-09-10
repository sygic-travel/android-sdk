package com.sygic.travel.sdk.directions.model

import android.support.annotation.ColorInt
import java.io.Serializable

@Suppress("unused")
class DirectionLeg(
	val startTime: DirectionTime,
	val endTime: DirectionTime,
	/** Duration in seconds */
	val duration: Int,
	/** Distance in meters */
	val distance: Int,
	val mode: DirectionLegTransportType,
	val polyline: String,
	val origin: DirectionLegStop,
	val destination: DirectionLegStop,
	val intermediateStops: List<DirectionLegStop>,
	val displayInfo: DisplayInfo,
	val attribution: DirectionAttribution?
) : Serializable {
	class DisplayInfo(
		val headsign: String?,
		val nameShort: String?,
		@ColorInt
		val lineColor: Int?,
		val displayMode: String?
	) : Serializable
}
