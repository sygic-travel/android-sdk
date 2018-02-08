package com.sygic.travel.sdk.tours.service

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.tours.facade.ToursGetYourGuideQuery
import com.sygic.travel.sdk.tours.facade.ToursViatorQuery
import com.sygic.travel.sdk.tours.model.Tour
import com.sygic.travel.sdk.utils.DateTimeHelper
import com.sygic.travel.sdk.utils.timeSeconds

internal class ToursService(
	private val apiClient: SygicTravelApiClient
) {
	fun getToursViator(query: ToursViatorQuery): List<Tour> {
		val request = apiClient.getToursViator(
			parentPlaceId = query.parentPlaceId,
			page = query.page,
			sortBy = query.sortBy?.apiSortBy,
			sortDirection = query.sortDirection?.apiSortDirection
		)
		return request.execute().body()!!.data!!.getTours()
	}

	fun getToursGetYourGuide(query: ToursGetYourGuideQuery): List<Tour> {
		val request = apiClient.getToursGetYourGuide(
			query = query.query,
			bounds = query.bounds?.toApiQueryString(),
			parentPlaceId = query.parentPlaceId,
			tags = query.tags,
			from = DateTimeHelper.timestampToDatetime(query.startDate?.timeSeconds),
			to = DateTimeHelper.timestampToDatetime(query.endDate?.timeSeconds),
			duration = query.getApiDurationQuery(),
			page = query.page,
			count = query.count,
			sortBy = query.sortBy?.apiSortBy,
			sortDirection = query.sortDirection?.apiSortDirection
		)
		return request.execute().body()!!.data!!.getTours()
	}
}
