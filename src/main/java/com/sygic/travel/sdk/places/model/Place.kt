package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import org.threeten.bp.Duration

@Suppress("unused")
open class Place constructor(
	val id: String,
	val level: Level,
	val categories: Set<Category>,
	val marker: String,
	val classSlug: String,
	val className: String?,
	val rating: Double,
	val ratingLocal: Double,
	val quadkey: String,
	val location: LatLng,
	val name: String,
	val nameSuffix: String?,
	val nameLocal: String?,
	val nameEn: String?,
	val nameTranslated: String?,
	val boundingBox: LatLngBounds?,
	val perex: String?,
	val url: String?,
	val thumbnailUrl: String?,
	val parents: List<Parent>,
	val hotelStarRating: Float?,
	val hotelStarRatingUnofficial: Float?,
	val customerRating: Float?,
	val durationEstimate: Duration?,
	val tagKeys: List<TagKey>,
	val isDeleted: Boolean,
	val ownerId: String?
) {
	override fun equals(other: Any?): Boolean {
		return id == (other as? Place)?.id
	}

	override fun hashCode(): Int {
		return id.hashCode()
	}

	data class Parent(
		val id: String,
		val name: String?,
		val level: Level
	)
}
