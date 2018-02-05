package com.sygic.travel.sdk.tours.service

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.places.model.query.ToursGetYourGuideQuery
import com.sygic.travel.sdk.places.model.query.ToursViatorQuery
import com.sygic.travel.sdk.tours.model.Tour

internal class ToursService(
	private val apiClient: SygicTravelApiClient
) {
	fun getToursViator(toursViatorQuery: ToursViatorQuery): List<Tour> {
		val request = apiClient.getToursViator(
			parentPlaceId = toursViatorQuery.parentPlaceId,
			page = toursViatorQuery.page,
			sortBy = toursViatorQuery.sortBy?.apiSortBy,
			sortDirection = toursViatorQuery.sortDirection?.apiSortDirection
		)
		return request.execute().body()!!.data!!.getTours()
	}

	fun getToursGetYourGuide(toursGetYourGuideQuery: ToursGetYourGuideQuery): List<Tour> {
		val request = apiClient.getToursGetYourGuide(
			query = toursGetYourGuideQuery.query,
			bounds = toursGetYourGuideQuery.bounds,
			parentPlaceId = toursGetYourGuideQuery.parentPlaceId,
			tags = toursGetYourGuideQuery.tags,
			from = toursGetYourGuideQuery.from,
			to = toursGetYourGuideQuery.to,
			duration = toursGetYourGuideQuery.duration,
			page = toursGetYourGuideQuery.page,
			count = toursGetYourGuideQuery.count,
			sortBy = toursGetYourGuideQuery.sortBy?.apiSortBy,
			sortDirection = toursGetYourGuideQuery.sortDirection?.apiSortDirection
		)
		return request.execute().body()!!.data!!.getTours()
	}
}
