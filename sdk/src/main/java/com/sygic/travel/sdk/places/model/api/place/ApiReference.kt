package com.sygic.travel.sdk.places.model.api.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.places.model.Reference
import com.sygic.travel.sdk.places.model.api.converter.ApiModel

/**
 * Link (Wiki, web site, etc.) related to a specific place.
 */
internal class ApiReference : ApiModel<Reference> {

	@SerializedName("id")
	var id: Int = 0

	@SerializedName("title")
	var title: String? = null

	@SerializedName("type")
	var type: String? = null

	@SerializedName("language_id")
	var languageId: String? = null

	@SerializedName("url")
	var url: String? = null

	@SerializedName("supplier")
	var supplier: String? = null

	@SerializedName("priority")
	var priority: Int = 0

	@SerializedName("currency")
	var currency: String? = null

	@SerializedName("price")
	var price: Float = 0.toFloat()

	@SerializedName("flags")
	var flags: List<String>? = null

	override fun convert(): Reference {
		val reference = Reference()

		reference.id = id
		reference.title = title
		reference.type = type
		reference.languageId = languageId
		reference.url = url
		reference.supplier = supplier
		reference.priority = priority
		reference.currency = currency
		reference.price = price
		reference.flags = flags

		return reference
	}
}
