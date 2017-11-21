package com.sygic.travel.sdk.tours.facade

import com.sygic.travel.sdk.places.model.query.ToursQuery
import com.sygic.travel.sdk.tours.model.Tour
import com.sygic.travel.sdk.tours.service.ToursService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class ToursFacade internal constructor(
	private val toursService: ToursService
) {
	/**
	 * Creates and sends a request to get the Tours.
	 * @param toursQuery ToursQuery encapsulating data for API request.
	 */
	fun getTours(toursQuery: ToursQuery): List<Tour>? {
		checkNotRunningOnMainThread()
		return toursService.getTours(toursQuery)
	}
}
