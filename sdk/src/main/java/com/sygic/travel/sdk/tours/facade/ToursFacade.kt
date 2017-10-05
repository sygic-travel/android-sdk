package com.sygic.travel.sdk.tours.facade

import com.sygic.travel.sdk.Callback
import com.sygic.travel.sdk.places.model.query.ToursQuery
import com.sygic.travel.sdk.tours.model.Tour
import com.sygic.travel.sdk.tours.service.ToursService
import com.sygic.travel.sdk.utils.runAsync
import com.sygic.travel.sdk.utils.runWithCallback


class ToursFacade(private val toursService: ToursService) {
	/**
	 * Creates and sends a request to get the Tours.
	 * @param toursQuery ToursQuery encapsulating data for API request.
	 */
	fun getTours(toursQuery: ToursQuery, callback: Callback<List<Tour>?>) {
		runWithCallback({ toursService.getTours(toursQuery) }, callback)
	}

	/**
	 * Creates and sends a request to get the Tours.
	 * @param toursQuery ToursQuery encapsulating data for API request.
	 */
	suspend fun getTours(toursQuery: ToursQuery): List<Tour>? {
		return runAsync { toursService.getTours(toursQuery) }
	}
}
