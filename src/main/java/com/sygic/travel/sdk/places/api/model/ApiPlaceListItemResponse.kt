package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.api.TripCategoryConverter
import com.sygic.travel.sdk.places.api.TripLevelConverter
import com.sygic.travel.sdk.places.model.Level
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.TagKey
import org.threeten.bp.Duration

@Suppress("MemberVisibilityCanBePrivate")
@JsonClass(generateAdapter = true)
internal class ApiPlaceListItemResponse(
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
	val place_class: PlaceClass,
	val parents: List<PlaceParent>,
	val hotel_star_rating: Float?,
	val hotel_star_rating_unofficial: Float?,
	val customer_rating: Float?,
	val duration_estimate: Int?,
	val tag_keys: List<String>,
	val owner_id: String?
) {
	companion object {
		@JsonClass(generateAdapter = true)
		internal class PlaceClass(
			val slug: String,
			val name: String?
		)

		@JsonClass(generateAdapter = true)
		internal class PlaceParent(
			val id: String,
			val name: String?,
			val level: String?
		)
	}

	fun fromApi(): Place {
		return Place(
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
			tagKeys = tag_keys.map { tagKey -> TagKey(tagKey) },
			ownerId = owner_id,
			isDeleted = false
		)
	}
}
