package com.sygic.travel.sdk.places.facade

import com.sygic.travel.sdk.Callback
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.media.Medium
import com.sygic.travel.sdk.places.model.query.PlacesQuery
import com.sygic.travel.sdk.places.service.PlacesService
import com.sygic.travel.sdk.utils.runAsync
import com.sygic.travel.sdk.utils.runWithCallback


/**
 * Data provider contains methods for fetching data either from API or a database.
 */
class PlacesFacade(private val placesService: PlacesService) {

	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param placesQuery PlacesQuery encapsulating data for API request.
	 */
	fun getPlaces(placesQuery: PlacesQuery, callback: Callback<List<Place>?>) {
		runWithCallback({ placesService.getPlaces(placesQuery) }, callback)
	}


	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param placesQuery PlacesQuery encapsulating data for API request.
	 */
	suspend fun getPlaces(placesQuery: PlacesQuery): List<Place>? {
		return runAsync { placesService.getPlaces(placesQuery) }
	}


	/**
	 * Creates and sends a request to get place with detailed information.
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 */
	fun getPlaceDetailed(id: String, callback: Callback<Place?>) {
		runWithCallback({ placesService.getPlaceDetailed(id) }, callback)
	}


	/**
	 * Creates and sends a request to get place with detailed information.
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 */
	suspend fun getPlaceDetailed(id: String): Place? {
		return runAsync { placesService.getPlaceDetailed(id) }
	}


	/**
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 */
	fun getPlacesDetailed(ids: List<String>, callback: Callback<List<Place>?>) {
		runWithCallback({ placesService.getPlacesDetailed(ids) }, callback)
	}


	/**
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 */
	suspend fun getPlacesDetailed(ids: List<String>): List<Place>? {
		return runAsync { placesService.getPlacesDetailed(ids) }
	}


	/**
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 */
	fun getPlaceMedia(id: String, callback: Callback<List<Medium>?>) {
		runWithCallback({ placesService.getPlaceMedia(id) }, callback)
	}


	/**
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 */
	suspend fun getPlaceMedia(id: String): List<Medium>? {
		return runAsync { placesService.getPlaceMedia(id) }
	}
}
