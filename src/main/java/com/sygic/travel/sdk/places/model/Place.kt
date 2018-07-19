package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import com.sygic.travel.sdk.places.model.geo.LatLng

@Suppress("unused")
open class Place(
	val id: String,
	val level: Level,
	val categories: Set<Category>,
	val rating: Float,
	val quadkey: String,
	val location: LatLng,
	val name: String,
	val nameSuffix: String?,
	val boundingBox: LatLngBounds?,
	val perex: String?,
	val url: String?,
	val thumbnailUrl: String?,
	val marker: String,
	val parentIds: Set<String>,
	val starRating: Float?,
	val starRatingUnofficial: Float?,
	val customerRating: Float?,
	val duration: Int?,
	val ownerId: String?
) {
	override fun equals(other: Any?): Boolean {
		return id == (other as? Place)?.id
	}

	override fun hashCode(): Int {
		return id.hashCode()
	}
}
