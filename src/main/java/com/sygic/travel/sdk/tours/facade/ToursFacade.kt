package com.sygic.travel.sdk.tours.facade

import com.sygic.travel.sdk.places.model.query.ToursGetYourGuideQuery
import com.sygic.travel.sdk.places.model.query.ToursViatorQuery
import com.sygic.travel.sdk.tours.model.Tour
import com.sygic.travel.sdk.tours.service.ToursService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class ToursFacade internal constructor(
	private val toursService: ToursService
) {
	/**
	 * Creates and sends a request to get the Viator Tours.
	 */
	fun getToursViator(toursViatorQuery: ToursViatorQuery): List<Tour> {
		checkNotRunningOnMainThread()
		return toursService.getToursViator(toursViatorQuery)
	}

	/**
	 * Creates and sends a request to get the Get Your Guide Tours.
	 */
	fun getToursGetYourGuide(toursGetYourGuideQuery: ToursGetYourGuideQuery): List<Tour> {
		checkNotRunningOnMainThread()
		return toursService.getToursGetYourGuide(toursGetYourGuideQuery)
	}
}
