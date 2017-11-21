package com.sygic.travel.sdk.tours.service

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.places.model.query.ToursQuery
import com.sygic.travel.sdk.tours.model.Tour

internal class ToursService(
	private val apiClient: SygicTravelApiClient
) {
	fun getTours(toursQuery: ToursQuery): List<Tour>? {
		val request = apiClient.getTours(
			destinationId = toursQuery.destinationId,
			page = toursQuery.page,
			sortBy = toursQuery.sortBy?.string,
			sortDirection = toursQuery.sortDirection?.string
		)
		return request.execute().body()?.data?.getTours()
	}
}
