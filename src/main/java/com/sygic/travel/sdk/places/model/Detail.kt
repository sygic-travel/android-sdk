package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.media.Medium

/**
 * Place detailed information.
 */
@Suppress("unused")
class Detail constructor(
	val tags: List<Tag>,
	val description: Description?,
	val address: String?,
	val admission: String?,
	val email: String?,
	val openingHoursRaw: String?,
	val openingHoursNote: String?,
	val phone: String?,
	val mediumSquare: Medium?,
	val mediumLandscape: Medium?,
	val mediumPortrait: Medium?,
	val mediumVideoPreview: Medium?,
	val references: List<Reference>,
	val area: Long?,
	val collectionCount: Int
)
