package com.sygic.travel.sdk.api.model.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.geo.Bounds
import com.sygic.travel.sdk.api.model.geo.Location
import com.sygic.travel.sdk.api.model.media.MainMedia

internal class PlaceDetail {
    // PLACE

	@SerializedName("id")
	var id: String? = null

	@SerializedName("level")
	var level: String? = null

	@SerializedName("categories")
	var categories: List<String>? = null

	@SerializedName("rating")
	var rating: Float = 0.toFloat()

	@SerializedName("quadkey")
	var quadkey: String? = null

	@SerializedName("location")
	var location: Location? = null

	@SerializedName("bounding_box")
	var bounds: Bounds? = null

	@SerializedName("name")
	var name: String? = null

	@SerializedName("name_suffix")
	var nameSuffix: String? = null

	@SerializedName("perex")
	var perex: String? = null

	@SerializedName("url")
	var url: String? = null

	@SerializedName("thumbnail_url")
	var thumbnailUrl: String? = null

	@SerializedName("marker")
	var marker: String? = null

	@SerializedName("parent_ids")
	var parentIds: List<String>? = null

	// DETAIL

	@SerializedName("tags")
	var tags: List<Tag>? = null

	@SerializedName("description")
	var description: Description? = null

	@SerializedName("address")
	var address: String? = null

	@SerializedName("admission")
	var admission: String? = null

	@SerializedName("duration")
	var duration: Int = 0

	@SerializedName("email")
	var email: String? = null

	@SerializedName("opening_hours")
	var openingHours: String? = null

	@SerializedName("phone")
	var phone: String? = null

	@SerializedName("main_media")
	var mainMedia: MainMedia? = null

	@SerializedName("references")
	var references: List<Reference>? = null
}