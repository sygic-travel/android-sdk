package com.sygic.travel.sdk.places.api.model

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.model.Description
import com.sygic.travel.sdk.places.model.Detail
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.Reference
import com.sygic.travel.sdk.places.model.Tag

class ApiPlaceItemResponse(
	id: String,
	level: String,
	categories: List<String>,
	rating: Float,
	quadkey: String,
	location: ApiLocationResponse,
	bounds: ApiBounds,
	name: String,
	nameSuffix: String?,
	perex: String?,
	url: String?,
	thumbnailUrl: String?,
	marker: String?,
	parentIds: List<String>,
	val tags: List<ApiTag>,
	val description: ApiDescription?,
	val address: String?,
	val admission: String?,
	val duration: Int?,
	val email: String?,
	@SerializedName("opening_hours")
	val openingHours: String?,
	val phone: String?,
	@SerializedName("main_media")
	val mainMedia: ApiMainMediaResponse?,
	val references: List<ApiReference>
) : ApiPlaceListItemResponse(
	id,
	level,
	categories,
	rating,
	quadkey,
	location,
	bounds,
	name,
	nameSuffix,
	perex,
	url,
	thumbnailUrl,
	marker,
	parentIds
) {

	class ApiDescription(
		val text: String,
		val provider: String?,
		@SerializedName("translation_provider")
		val translationProvider: String?,
		val link: String?
	) {
		fun fromApi(): Description {
			val description = Description()
			description.text = text
			description.provider = provider
			description.translationProvider = translationProvider
			description.link = link
			return description
		}
	}

	class ApiTag(
		val key: String,
		val name: String
	) {
		fun fromApi(): Tag {
			val tag = Tag()
			tag.key = key
			tag.name = name
			return tag
		}
	}

	class ApiReference(
		val id: Int,
		val title: String,
		val type: String,
		@SerializedName("language_id")
		val languageId: String?,
		val url: String,
		val supplier: String?,
		val priority: Int,
		val currency: String?,
		val price: Float?,
		val flags: List<String>
	) {
		fun fromApi(): Reference {
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

	override fun fromApi(): Place {
		val place = Place()
		fromApi(place)

		val detail = Detail()
		detail.tags = tags.map { it.fromApi() }
		detail.description = description?.fromApi()
		detail.address = address
		detail.admission = admission
		detail.duration = duration
		detail.email = email
		detail.openingHours = openingHours
		detail.phone = phone
		detail.mainMedia = mainMedia?.fromApi()
		detail.references = references.map { it.fromApi() }

		place.detail = detail
		return place
	}
}
