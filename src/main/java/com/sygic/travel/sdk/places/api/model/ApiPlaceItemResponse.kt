package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.common.Language
import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.api.TripCategoryConverter
import com.sygic.travel.sdk.places.api.TripLevelConverter
import com.sygic.travel.sdk.places.model.Description
import com.sygic.travel.sdk.places.model.Detail
import com.sygic.travel.sdk.places.model.DetailedPlace
import com.sygic.travel.sdk.places.model.Level
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.Reference
import com.sygic.travel.sdk.places.model.Tag
import com.sygic.travel.sdk.places.model.TagKey
import org.threeten.bp.Duration
import org.threeten.bp.ZoneId
import java.util.Locale

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
	val name_local: String?,
	val name_en: String?,
	val name_translated: String?,
	val perex: String?,
	val url: String?,
	val thumbnail_url: String?,
	val marker: String,
	@Json(name = "class")
	val place_class: ApiPlaceListItemResponse.Companion.PlaceClass,
	val parents: List<ApiPlaceListItemResponse.Companion.PlaceParent>,
	val hotel_star_rating: Float?,
	val hotel_star_rating_unofficial: Float?,
	val customer_rating: Float?,
	val duration_estimate: Int?,
	val owner_id: String?,
	val tags: List<ApiTag>,
	val description: ApiDescription?,
	val address: String?,
	val address_is_approximated: Boolean,
	val admission: String?,
	val email: String?,
	val opening_hours_note: String?,
	val opening_hours_raw: String?,
	val timezone: String?,
	val phone: String?,
	val main_media: ApiMainMediaResponse?,
	val references: List<ApiReference>,
	val is_deleted: Boolean,
	val area: Long?,
	val collection_count: Int?,
	val attributes: Map<String, String>?
) {
	@JsonClass(generateAdapter = true)
	internal class ApiDescription(
		val text: String,
		val provider: String?,
		val translation_provider: String?,
		val link: String?,
		val language_id: String?
	) {
		fun fromApi(): Description {
			return Description(
				text = text,
				provider = provider,
				providerLink = link,
				translationProvider = translation_provider,
				language = language_id?.let {
					Language.valueOf(language_id.toUpperCase(Locale.ROOT))
				}
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
			addressApproximated = address_is_approximated,
			admission = admission,
			email = email,
			openingHoursNote = opening_hours_note,
			openingHoursRaw = opening_hours_raw,
			phone = phone,
			mediumSquare = media?.get(main_media?.usage?.square),
			mediumLandscape = media?.get(main_media?.usage?.landscape),
			mediumPortrait = media?.get(main_media?.usage?.portrait),
			mediumVideoPreview = media?.get(main_media?.usage?.video_preview),
			references = references.map { it.fromApi() },
			area = area,
			collectionCount = collection_count ?: 0,
			timezone = timezone?.let {
				try {
					ZoneId.of(timezone)
				} catch (e: Throwable) {
					e.printStackTrace()
					null
				}
			},
			attributes = attributes
		)

		return DetailedPlace(
			id = id,
			level = TripLevelConverter.fromApiLevel(level),
			categories = categories.mapNotNull { TripCategoryConverter.fromApiCategory(it) }.toSet(),
			marker = marker,
			classSlug = place_class.slug,
			className = place_class.name,
			rating = rating,
			ratingLocal = rating_local,
			quadkey = quadkey,
			location = location.fromApi(),
			boundingBox = bounding_box?.fromApi(),
			name = name,
			nameSuffix = name_suffix,
			nameLocal = name_local,
			nameEn = name_en,
			nameTranslated = name_translated,
			perex = perex,
			url = url,
			thumbnailUrl = thumbnail_url,
			parents = parents.map { placeParent ->
				Place.Parent(
					placeParent.id,
					placeParent.name,
					placeParent.level?.let { TripLevelConverter.fromApiLevel(it) } ?: Level.POI
				)
			},
			hotelStarRating = hotel_star_rating,
			hotelStarRatingUnofficial = hotel_star_rating_unofficial,
			customerRating = customer_rating,
			durationEstimate = duration_estimate?.let { Duration.ofSeconds(it.toLong()) },
			tagKeys = tags.map { tag -> TagKey(tag.key) },
			isDeleted = is_deleted,
			ownerId = owner_id,
			detail = detail
		)
	}
}
