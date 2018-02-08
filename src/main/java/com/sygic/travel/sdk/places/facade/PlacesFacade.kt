package com.sygic.travel.sdk.places.facade

import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.PlaceInfo
import com.sygic.travel.sdk.places.model.media.Medium
import com.sygic.travel.sdk.places.service.PlacesService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

/**
 * Places facade provides interface for fetching places data from the API.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class PlacesFacade internal constructor(
	private val placesService: PlacesService
) {
	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param placesQuery PlacesQuery encapsulating data for API request.
	 */
	fun getPlaces(placesQuery: PlacesQuery): List<PlaceInfo>? {
		checkNotRunningOnMainThread()
		return placesService.getPlaces(placesQuery)
	}

	/**
	 * Creates and sends a request to get place with detailed information.
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 */
	fun getPlaceDetailed(id: String): Place? {
		checkNotRunningOnMainThread()
		return placesService.getPlaceDetailed(id)
	}

	/**
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 */
	fun getPlacesDetailed(ids: List<String>): List<Place>? {
		checkNotRunningOnMainThread()
		return placesService.getPlacesDetailed(ids)
	}

	/**
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 */
	fun getPlaceMedia(id: String): List<Medium>? {
		checkNotRunningOnMainThread()
		return placesService.getPlaceMedia(id)
	}
}
