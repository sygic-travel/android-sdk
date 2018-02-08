package com.sygic.travel.sdk.tours.facade

import com.sygic.travel.sdk.places.model.query.ToursGetYourGuideQuery
import com.sygic.travel.sdk.places.model.query.ToursViatorQuery
import com.sygic.travel.sdk.tours.model.Tour
import com.sygic.travel.sdk.tours.service.ToursService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

/**
 * Tours facade provides interface for accessing tours from Viator and GetYourGuide providers through Sygic Travel API.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class ToursFacade internal constructor(
	private val toursService: ToursService
) {
	/**
	 * Creates and sends a request to get the Viator Tours.
	 */
	fun getToursViator(query: ToursViatorQuery): List<Tour> {
		checkNotRunningOnMainThread()
		return toursService.getToursViator(query)
	}

	/**
	 * Creates and sends a request to get the Get Your Guide Tours.
	 */
	fun getToursGetYourGuide(query: ToursGetYourGuideQuery): List<Tour> {
		checkNotRunningOnMainThread()
		return toursService.getToursGetYourGuide(query)
	}
}
