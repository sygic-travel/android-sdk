package com.sygic.travel.sdk.directions.model

import java.io.Serializable

@Suppress("unused")
class Direction(
	val mode: DirectionMode,
	/** Duration in seconds */
	val duration: Int?,
	/** Distance in meters */
	val distance: Int?,
	val transferCount: Int,
	val routeId: String?,
	val source: String,
	val legs: List<DirectionLeg>,
	val attributions: List<DirectionAttribution>
) : Serializable
