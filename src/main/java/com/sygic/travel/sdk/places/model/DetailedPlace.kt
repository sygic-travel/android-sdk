package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import com.sygic.travel.sdk.places.model.geo.LatLng

@Suppress("unused")
class DetailedPlace constructor(
	id: String,
	level: Level,
	categories: Set<Category>,
	rating: Double,
	ratingLocal: Double,
	quadkey: String,
	location: LatLng,
	name: String,
	nameSuffix: String?,
	originalName: String?,
	boundingBox: LatLngBounds?,
	perex: String?,
	url: String?,
	thumbnailUrl: String?,
	marker: String,
	parentIds: Set<String>,
	starRating: Float?,
	starRatingUnofficial: Float?,
	customerRating: Float?,
	duration: Int?,
	ownerId: String?,
	isDeleted: Boolean,
	val detail: Detail
) : Place(
	id,
	level,
	categories,
	rating,
	ratingLocal,
	quadkey,
	location,
	name,
	nameSuffix,
	originalName,
	boundingBox,
	perex,
	url,
	thumbnailUrl,
	marker,
	parentIds,
	starRating,
	starRatingUnofficial,
	customerRating,
	duration,
	ownerId,
	isDeleted
)
