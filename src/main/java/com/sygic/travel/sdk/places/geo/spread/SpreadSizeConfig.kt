package com.sygic.travel.sdk.places.geo.spread

/**
 *
 * Place configuration values for spreading.
 */
class SpreadSizeConfig
/**
 *
 * Place configuration values for spreading.
 * @param radius Circular marker's radius.
 * *
 * @param margin Marker's minimal margin.
 * *
 * @param name String representation of size. One of following values:
 * *
 * *      * [SpreadSizeConfig.SMALL]
 * *      * [SpreadSizeConfig.MEDIUM]
 * *      * [SpreadSizeConfig.BIG]
 * *      * [SpreadSizeConfig.POPULAR]
 * *
 * *
 * @param isPhotoRequired Flag whether marker photo is required.
 * *
 * @param minimalRating Minimal rating to show the place's marker.
 */
(
	val radius: Int,
	val margin: Int,
	val name: String,
	val isPhotoRequired: Boolean,
	val minimalRating: Float
) {
	companion object {
		val SMALL = "small"
		val MEDIUM = "medium"
		val BIG = "big"
		val POPULAR = "popular"
		val INVISIBLE = "invisible"
	}
}
