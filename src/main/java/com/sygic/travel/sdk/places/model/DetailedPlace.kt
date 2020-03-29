package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import org.threeten.bp.Duration

@Suppress("unused")
class DetailedPlace constructor(
	id: String,
	level: Level,
	categories: Set<Category>,
	marker: String,
	classSlug: String,
	className: String?,
	rating: Double,
	ratingLocal: Double,
	quadkey: String,
	location: LatLng,
	name: String,
	nameSuffix: String?,
	nameLocal: String?,
	nameEn: String?,
	nameTranslated: String?,
	boundingBox: LatLngBounds?,
	perex: String?,
	url: String?,
	thumbnailUrl: String?,
	parents: List<Parent>,
	hotelStarRating: Float?,
	hotelStarRatingUnofficial: Float?,
	customerRating: Float?,
	durationEstimate: Duration?,
	tagKeys: List<TagKey>,
	isDeleted: Boolean,
	ownerId: String?,
	val detail: Detail
) : Place(
	id,
	level,
	categories,
	marker,
	classSlug,
	className,
	rating,
	ratingLocal,
	quadkey,
	location,
	name,
	nameSuffix,
	nameLocal,
	nameEn,
	nameTranslated,
	boundingBox,
	perex,
	url,
	thumbnailUrl,
	parents,
	hotelStarRating,
	hotelStarRatingUnofficial,
	customerRating,
	durationEstimate,
	tagKeys,
	isDeleted,
	ownerId
)
