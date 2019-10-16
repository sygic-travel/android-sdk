package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import org.threeten.bp.Duration

@Suppress("unused")
open class Place constructor(
	val id: String,
	val level: Level,
	val categories: Set<Category>,
	val rating: Double,
	val ratingLocal: Double,
	val quadkey: String,
	val location: LatLng,
	val name: String,
	val nameSuffix: String?,
	val originalName: String?,
	val boundingBox: LatLngBounds?,
	val perex: String?,
	val url: String?,
	val thumbnailUrl: String?,
	val marker: String,
	val parentIds: Set<String>,
	val starRating: Float?,
	val starRatingUnofficial: Float?,
	val customerRating: Float?,
	val duration: Duration?,
	val ownerId: String?,
	val isDeleted: Boolean
) {
	override fun equals(other: Any?): Boolean {
		return id == (other as? Place)?.id
	}

	override fun hashCode(): Int {
		return id.hashCode()
	}
}
