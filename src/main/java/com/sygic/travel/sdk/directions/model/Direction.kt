package com.sygic.travel.sdk.directions.model

import org.threeten.bp.Duration
import java.io.Serializable

@Suppress("unused")
class Direction(
	val mode: DirectionMode,
	val duration: Duration?,
	/** Distance in meters */
	val distance: Int?,
	val transferCount: Int,
	val routeId: String?,
	val source: String,
	val legs: List<DirectionLeg>,
	val attributions: List<DirectionAttribution>
) : Serializable
