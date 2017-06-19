package com.sygic.travel.sdk.api.model.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.geo.ApiBounds
import com.sygic.travel.sdk.api.model.geo.ApiLocation
import com.sygic.travel.sdk.api.model.media.ApiMainMedia
import com.sygic.travel.sdk.model.place.Detail
import com.sygic.travel.sdk.model.place.Place

internal class ApiPlaceDetail {
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
	var location: ApiLocation? = null

	@SerializedName("bounding_box")
	var bounds: ApiBounds? = null

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
	var tags: List<ApiTag>? = null

	@SerializedName("description")
	var description: ApiDescription? = null

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
	var mainMedia: ApiMainMedia? = null

	@SerializedName("references")
	var references: List<ApiReference>? = null

	fun convert(): Place{
		val place = Place()
		
		place.id = id
		place.level = level
		place.categories = categories
		place.rating = rating
		place.quadkey = quadkey
		place.location= location?.convert()
		place.bounds = bounds?.convert()
		place.name = name
		place.nameSuffix = nameSuffix
		place.perex = perex
		place.url = url
		place.thumbnailUrl = thumbnailUrl
		place.marker = marker
		place.parentIds = parentIds
		place.detail = convertDetail()
		
		return place
	}

	fun convertDetail(): Detail {
		val detail = Detail()
		
		val convertedTags: MutableList<com.sygic.travel.sdk.model.place.Tag> = mutableListOf()
		tags?.mapTo(convertedTags) {
			it.convert()
		}

		val convertedReferences: MutableList<com.sygic.travel.sdk.model.place.Reference> = mutableListOf()
		references?.mapTo(convertedReferences){
			it.convert()
		}

		detail.tags = convertedTags
		detail.description = description?.convert()
		detail.address = address
		detail.admission = admission
		detail.duration = duration
		detail.email = email
		detail.openingHours = openingHours
		detail.phone = phone
		detail.mainMedia = mainMedia?.convert()
		detail.references = convertedReferences

		return detail
	}
}