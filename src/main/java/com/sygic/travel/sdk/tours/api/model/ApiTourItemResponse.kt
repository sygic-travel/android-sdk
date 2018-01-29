package com.sygic.travel.sdk.tours.api.model

import com.sygic.travel.sdk.tours.model.Tour

internal class ApiTourItemResponse(
	val id: String,
	val supplier: String,
	val title: String,
	val perex: String,
	val url: String,
	val rating: Float,
	val reviewCount: Int,
	val photoUrl: String,
	val price: Float,
	val originalPrice: Float,
	val duration: String,
	val durationMin: Int,
	val durationMax: Int,
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
		tour.reviewCount = reviewCount
		tour.photoUrl = photoUrl
		tour.price = price
		tour.originalPrice = originalPrice
		tour.duration = duration
		tour.durationMin = durationMin
		tour.durationMax = durationMax
		tour.flags = flags
		return tour
	}
}
