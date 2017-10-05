package com.sygic.travel.sdk.places.model.api.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.places.model.Tag
import com.sygic.travel.sdk.places.model.api.converter.ApiModel

internal class ApiTag : ApiModel<Tag> {

	@SerializedName("key")
	var key: String? = null

	@SerializedName("name")
	var name: String? = null

	override fun convert(): Tag {
		val tag = Tag()

		tag.key = key
		tag.name = name

		return tag
	}
}
