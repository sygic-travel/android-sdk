package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.Json
import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.api.TripCategoryConverter
import com.sygic.travel.sdk.places.api.TripLevelConverter
import com.sygic.travel.sdk.places.model.Description
import com.sygic.travel.sdk.places.model.DescriptionProvider
import com.sygic.travel.sdk.places.model.Detail
import com.sygic.travel.sdk.places.model.DetailedPlace
import com.sygic.travel.sdk.places.model.Reference
import com.sygic.travel.sdk.places.model.Tag
import com.sygic.travel.sdk.places.model.TranslationProvider
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlaceItemResponse(
	val id: String,
	val level: String,
	val categories: List<String>,
	val rating: Float,
	val quadkey: String,
	val location: ApiLocationResponse,
	val bounding_box: ApiBoundsResponse?,
	val name: String,
	val name_suffix: String?,
	val perex: String?,
	val url: String?,
	val thumbnail_url: String?,
	val marker: String,
	val parent_ids: List<String>,
	val star_rating: Float?,
	val star_rating_unofficial: Float?,
	val customer_rating: Float?,
	val owner_id: String?,
	val tags: List<ApiTag>,
	val description: ApiDescription?,
	val address: String?,
	val admission: String?,
	val duration: Int?,
	val email: String?,
	@Json(name = "opening_hours")
	val openingHours: String?,
	val phone: String?,
	@Json(name = "main_media")
	val mainMedia: ApiMainMediaResponse?,
	val references: List<ApiReference>
) {
	class ApiDescription(
		val text: String,
		val provider: String?,
		@Json(name = "translation_provider")
		val translationProvider: String?,
		val link: String?
	) {
		companion object {
			private const val PROVIDER_WIKIPEDIA = "wikipedia"
			private const val PROVIDER_WIKIVOYAGE = "wikivoyage"
			private const val TRANSLATION_PROVIDER_GOOGLE = "google"
		}

		fun fromApi(): Description {
			return Description(
				text = text,
				provider = when (provider) {
					PROVIDER_WIKIPEDIA -> DescriptionProvider.WIKIPEDIA
					PROVIDER_WIKIVOYAGE -> DescriptionProvider.WIKIVOYAGE
					else -> DescriptionProvider.NONE
				},
				translationProvider = when (translationProvider) {
					TRANSLATION_PROVIDER_GOOGLE -> TranslationProvider.GOOGLE
					else -> TranslationProvider.NONE
				},
				link = link
			)
		}
	}

	class ApiTag(
		val key: String,
		val name: String
	) {
		fun fromApi(): Tag {
			return Tag(
				key = key,
				name = name
			)
		}
	}

	class ApiReference(
		val id: Int,
		val title: String,
		val type: String,
		@Json(name = "language_id")
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

	fun fromApi(): DetailedPlace {
		val detail = Detail(
			tags = tags.map { it.fromApi() },
			description = description?.fromApi(),
			address = address,
			admission = admission,
			duration = duration,
			email = email,
			openingHours = openingHours,
			phone = phone,
			mainMedia = mainMedia?.media?.map {
				it.fromApi()
			} ?: emptyList(),
			references = references.map { it.fromApi() }
		)

		return DetailedPlace(
			id = id,
			level = TripLevelConverter.fromApiLevel(level),
			categories = TripCategoryConverter.fromApiCategories(categories),
			rating = rating,
			quadkey = quadkey,
			location = location.fromApi(),
			boundingBox = bounding_box?.fromApi(),
			name = name,
			nameSuffix = name_suffix,
			perex = perex,
			url = url,
			thumbnailUrl = thumbnail_url,
			marker = marker,
			parentIds = parent_ids.toSet(),
			detail = detail,
			starRating = star_rating,
			starRatingUnofficial = star_rating_unofficial,
			customerRating = customer_rating,
			ownerId = owner_id
		)
	}
}
