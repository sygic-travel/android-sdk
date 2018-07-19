package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import com.sygic.travel.sdk.places.model.geo.LatLng

@Suppress("unused")
class DetailedPlace(
	id: String,
	level: Level,
	categories: Set<Category>,
	rating: Float,
	quadkey: String,
	location: LatLng,
	name: String,
	nameSuffix: String?,
	boundingBox: LatLngBounds?,
	perex: String?,
	url: String?,
	thumbnailUrl: String?,
	marker: String,
	parentIds: Set<String>,
	starRating: Float?,
	starRatingUnofficial: Float?,
	customerRating: Float?,
	ownerId: String?,
	duration: Int?,
	val detail: Detail
) : Place(
	id,
	level,
	categories,
	rating,
	quadkey,
	location,
	name,
	nameSuffix,
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
	ownerId
)
