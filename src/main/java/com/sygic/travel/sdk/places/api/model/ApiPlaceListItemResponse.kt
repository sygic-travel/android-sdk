package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.api.TripCategoryConverter
import com.sygic.travel.sdk.places.api.TripLevelConverter
import com.sygic.travel.sdk.places.model.Place

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
	val owner_id: String?
) {
	fun fromApi(): Place {
		return Place(
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
			starRating = star_rating,
			starRatingUnofficial = star_rating_unofficial,
			customerRating = customer_rating,
			duration = duration,
			ownerId = owner_id,
			isDeleted = false
		)
	}
}
