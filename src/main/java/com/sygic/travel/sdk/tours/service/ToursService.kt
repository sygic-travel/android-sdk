package com.sygic.travel.sdk.tours.service

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.checkedExecute
import com.sygic.travel.sdk.tours.facade.ToursGetYourGuideQuery
import com.sygic.travel.sdk.tours.facade.ToursViatorQuery
import com.sygic.travel.sdk.tours.model.Tour
import org.threeten.bp.format.DateTimeFormatter

internal class ToursService constructor(
	private val apiClient: SygicTravelApiClient
) {
	fun getToursViator(query: ToursViatorQuery): List<Tour> {
		return apiClient.getToursViator(
			parentPlaceId = query.parentPlaceId,
			page = query.page,
			sortBy = query.sortBy?.apiSortBy,
			sortDirection = query.sortDirection?.apiSortDirection
		).checkedExecute().body()!!.data!!.fromApi()
	}

	fun getToursGetYourGuide(query: ToursGetYourGuideQuery): List<Tour> {
		return apiClient.getToursGetYourGuide(
			query = query.query,
			bounds = query.bounds?.toApiExpression(),
			parentPlaceId = query.parentPlaceId,
			tags = query.tags,
			from = query.startDate?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
			to = query.endDate?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
			duration = query.getApiDurationQuery(),
			page = query.page,
			count = query.count,
			sortBy = query.sortBy?.apiSortBy,
			sortDirection = query.sortDirection?.apiSortDirection
		).checkedExecute().body()!!.data!!.fromApi()
	}
}
