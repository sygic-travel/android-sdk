package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.converter.ApiModel
import com.sygic.travel.sdk.api.model.geo.ApiLocation
import com.sygic.travel.sdk.model.media.Medium

/**
 * Place medium.
 */

internal class ApiMedium : ApiModel<Medium> {

	@SerializedName("id")
	var id: String? = null

	@SerializedName("type")
	var type: String? = null

	@SerializedName("url_template")
	var urlTemplate: String? = null

	@SerializedName("url")
	var url: String? = null

	@SerializedName("original")
	var original: ApiOriginal? = null

	@SerializedName("suitability")
	var suitability: List<String>? = null

	@SerializedName("created_at")
	var createdAt: String? = null

	@SerializedName("source")
	var source: ApiSource? = null

	@SerializedName("created_by")
	var createdBy: String? = null

	@SerializedName("quadkey")
	var quadkey: String? = null

	@SerializedName("attribution")
	var attribution: ApiAttribution? = null

	@SerializedName("location")
	var location: ApiLocation? = null

	override fun convert(): Medium {
		val medium = Medium()

		medium.id = id
		medium.type = type
		medium.urlTemplate = urlTemplate
		medium.url = url
		medium.original = original?.convert()
		medium.suitability = suitability
		medium.createdAt = createdAt
		medium.source = source?.convert()
		medium.createdBy = createdBy
		medium.quadkey = quadkey
		medium.attribution = attribution?.convert()
		medium.location = location?.convert()

		return medium
	}
}
