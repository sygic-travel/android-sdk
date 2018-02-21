package com.sygic.travel.sdk.tours.api.model

import com.sygic.travel.sdk.tours.model.Tour

internal class ApiTourItemResponse(
	val id: String,
	val supplier: String,
	val title: String,
	val perex: String,
	val url: String,
	val rating: Float,
	val review_count: Int,
	val photo_url: String,
	val price: Float,
	val original_price: Float,
	val duration: String,
	val duration_min: Int,
	val duration_max: Int,
	val flags: List<String>
) {
	fun fromApi(): Tour {
		val tour = Tour()
		tour.id = id
		tour.supplier = supplier
		tour.title = title
		tour.perex = perex
		tour.url = url
		tour.rating = rating
		tour.reviewCount = review_count
		tour.photoUrl = photo_url
		tour.price = price
		tour.originalPrice = original_price
		tour.duration = duration
		tour.durationMin = duration_min
		tour.durationMax = duration_max
		tour.flags = flags
		return tour
	}
}
