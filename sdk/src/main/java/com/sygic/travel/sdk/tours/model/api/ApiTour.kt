package com.sygic.travel.sdk.tours.model.api

import com.sygic.travel.sdk.places.model.api.converter.ApiModel
import com.sygic.travel.sdk.tours.model.Tour

internal class ApiTour : ApiModel<Tour> {
	var id: String? = null
	var supplier: String? = null
	var title: String? = null
	var perex: String? = null
	var url: String? = null
	var rating: Float? = null
	var reviewCount: Int? = null
	var photoUrl: String? = null
	var price: Float? = null
	var originalPrice: Float? = null
	var duration: String? = null

	override fun convert(): Tour {
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
