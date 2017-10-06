package com.sygic.travel.sdk.places.model

/**
 * Link (Wiki, web site, etc.) related to a specific place.
 */
class Reference {
	var id: Int = 0
	var title: String? = null
	var type: String? = null
	var languageId: String? = null
	var url: String? = null
	var supplier: String? = null
	var priority: Int = 0
	var currency: String? = null
	var price: Float = 0.toFloat()
	var flags: List<String>? = null
}
