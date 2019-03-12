package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.api.TripCategoryConverter
import com.sygic.travel.sdk.places.api.TripLevelConverter
import com.sygic.travel.sdk.places.model.Description
import com.sygic.travel.sdk.places.model.Detail
import com.sygic.travel.sdk.places.model.DetailedPlace
import com.sygic.travel.sdk.places.model.Reference
import com.sygic.travel.sdk.places.model.Tag

@Suppress("MemberVisibilityCanBePrivate")
@JsonClass(generateAdapter = true)
internal class ApiPlaceItemResponse(
	val id: String,
	val level: String,
	val categories: List<String>,
	val rating: Double,
	val rating_local: Double,
	val quadkey: String,
	val location: ApiLocationResponse,
	val bounding_box: ApiBoundsResponse?,
	val name: String,
	val name_suffix: String?,
	val original_name: String?,
	val perex: String?,
	val url: String?,
	val thumbnail_url: String?,
	val marker: String,
	val parent_ids: List<String>,
	val star_rating: Float?,
	val star_rating_unofficial: Float?,
	val customer_rating: Float?,
	val duration: Int?,
	val owner_id: String?,
	val tags: List<ApiTag>,
	val description: ApiDescription?,
	val address: String?,
	val admission: String?,
	val email: String?,
	val opening_hours: String?,
	val opening_hours_raw: String?,
	val phone: String?,
	val main_media: ApiMainMediaResponse?,
	val references: List<ApiReference>,
	val is_deleted: Boolean,
	val area: Long?,
	val collection_count: Int?
) {
	@JsonClass(generateAdapter = true)
	internal class ApiDescription(
		val text: String,
		val is_translated: Boolean,
		val provider: String?,
		val translation_provider: String?,
		val link: String?
	) {
		fun fromApi(): Description {
			return Description(
				text = text,
				provider = provider,
				providerLink = link,
				isTranslated = is_translated,
				translationProvider = translation_provider
			)
		}
	}

	@JsonClass(generateAdapter = true)
	internal class ApiTag(
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

	@JsonClass(generateAdapter = true)
	internal class ApiReference(
		val id: Int,
		val title: String,
		val type: String,
		val language_id: String?,
		val url: String,
		val supplier: String?,
		val priority: Int,
		val currency: String?,
		val price: Float?,
		val flags: List<String>,
		val offline_file: String?
	) {
		fun fromApi(): Reference {
			return Reference(
				id = id,
				title = title,
				type = type,
				languageId = language_id,
				url = url,
				supplier = supplier,
				priority = priority,
				currency = currency,
				price = price,
				flags = flags.toHashSet(),
				offlineFile = offline_file
			)
		}
	}

	fun fromApi(): DetailedPlace {
		val media = main_media?.media
			?.map { it.fromApi() }
			?.associateBy { it.id }

		val detail = Detail(
			tags = tags.map { it.fromApi() },
			description = description?.fromApi(),
			address = address,
			admission = admission,
			email = email,
			openingHoursNote = opening_hours,
			openingHoursRaw = opening_hours_raw,
			phone = phone,
			mediumSquare = media?.get(main_media?.usage?.square),
			mediumLandscape = media?.get(main_media?.usage?.landscape),
			mediumPortrait = media?.get(main_media?.usage?.portrait),
			mediumVideoPreview = media?.get(main_media?.usage?.video_preview),
			references = references.map { it.fromApi() },
			area = area,
			collectionCount = collection_count ?: 0
		)

		return DetailedPlace(
			id = id,
			level = TripLevelConverter.fromApiLevel(level),
			categories = categories.mapNotNull { TripCategoryConverter.fromApiCategory(it) }.toSet(),
			rating = rating,
			ratingLocal = rating_local,
			quadkey = quadkey,
			location = location.fromApi(),
			boundingBox = bounding_box?.fromApi(),
			name = name,
			nameSuffix = name_suffix,
			originalName = original_name,
			perex = perex,
			url = url,
			thumbnailUrl = thumbnail_url,
			marker = marker,
			parentIds = parent_ids.toSet(),
			detail = detail,
			starRating = star_rating,
			starRatingUnofficial = star_rating_unofficial,
			customerRating = customer_rating,
			ownerId = owner_id,
			duration = duration,
			isDeleted = is_deleted
		)
	}
}
