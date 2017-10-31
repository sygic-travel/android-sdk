package com.sygic.travel.sdk.tours.api.model

import com.sygic.travel.sdk.tours.model.Tour

class ApiTourItemResponse(
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
	val duration: String
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
		return tour
	}
}
