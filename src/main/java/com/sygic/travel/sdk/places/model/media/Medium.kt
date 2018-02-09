package com.sygic.travel.sdk.places.model.media

import com.sygic.travel.sdk.places.model.geo.Location

/**
 * DetailedPlace medium.
 */
class Medium(
	val id: String,
	val type: Type,
	val urlTemplate: String,
	val url: String,
	val original: Original?,
	val suitability: Set<Suitability>,
	val attribution: Attribution,
	val location: Location?
)
