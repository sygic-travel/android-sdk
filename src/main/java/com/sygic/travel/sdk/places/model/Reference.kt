package com.sygic.travel.sdk.places.model

/**
 * Link (Wiki, web site, etc.) related to a specific place.
 */
data class Reference constructor(
	val id: Int,
	val title: String,
	val type: String,
	val languageId: String?,
	val url: String,
	val supplier: String?,
	val priority: Int,
	val currency: String?,
	val price: Float?,
	val flags: Set<String>,
	/** @internal */
	val offlineFile: String?
)
