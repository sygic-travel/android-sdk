package com.sygic.travel.sdk.tours.model

import org.threeten.bp.Duration

/**
 * Model class for Tour.
 */
@Suppress("unused")
class Tour constructor(
	val id: String,
	val supplier: String,
	val title: String,
	val perex: String,
	val url: String,
	val rating: Float,
	val reviewCount: Int,
	/**
	 * In case of get_your_guide supplier the url contains a placeholder [format_id] which has to be replaced.
	 * See documentation of API endpoint response on http://docs.sygictravelapi.com/1.1/#section-tours.
	 */
	val photoUrl: String,
	val price: Float,
	val originalPrice: Float,
	val duration: String?,
	val durationMin: Duration?,
	val durationMax: Duration?,
	val flags: Set<String>
) {
	companion object {
		const val FLAG_BESTSELLER = "bestseller"
		const val FLAG_INSTANT_CONFIRMATION = "instant_confirmation"
		const val FLAG_PORTABLE_TICKET = "portable_ticket"
		const val FLAG_WHEELCHAIR_ACCESS = "wheelchair_access"
		const val FLAG_SKIP_THE_LINE = "skip_the_line"
	}
}
