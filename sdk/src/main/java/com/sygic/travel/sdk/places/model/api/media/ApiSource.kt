package com.sygic.travel.sdk.places.model.api.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.places.model.api.converter.ApiModel
import com.sygic.travel.sdk.places.model.media.Source

/**
 * Medium's source.
 */
internal class ApiSource : ApiModel<Source> {

	@SerializedName("name")
	var name: String? = null

	@SerializedName("external_id")
	var externalId: String? = null

	@SerializedName("provider")
	var provider: String? = null

	override fun convert(): Source {
		val source = Source()

		source.name = name
		source.externalId = externalId
		source.provider = provider

		return source
	}

}
