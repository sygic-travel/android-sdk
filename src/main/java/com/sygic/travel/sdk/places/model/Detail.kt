package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.media.Medium

/**
 * DetailedPlace detailed information.
 */
class Detail(
	val tags: List<Tag>,
	val description: Description?,
	val address: String?,
	val admission: String?,
	val duration: Int? = 0,
	val email: String?,
	val openingHours: String?,
	val phone: String?,
	val mainMedia: List<Medium>,
	val references: List<Reference>
)
