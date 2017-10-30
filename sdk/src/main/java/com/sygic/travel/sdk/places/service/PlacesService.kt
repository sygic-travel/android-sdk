package com.sygic.travel.sdk.places.service

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.media.Medium
import com.sygic.travel.sdk.places.model.query.PlacesQuery

class PlacesService(private val sygicTravelApiClient: SygicTravelApiClient) {
	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param placesQuery PlacesQuery encapsulating data for API request.
	 */
	fun getPlaces(
		placesQuery: PlacesQuery
	): List<Place>? {
		val request = sygicTravelApiClient.getPlaces(
			placesQuery.query,
			placesQuery.levelsQueryString,
			placesQuery.categoriesQueryString,
			placesQuery.mapTilesQueryString,
			placesQuery.mapSpread,
			placesQuery.boundsQueryString,
			placesQuery.tagsQueryString,
			placesQuery.parentsQueryString,
			placesQuery.limit
		)
		val response = request.execute()
		return response.body()?.getPlaces()
	}

	/**
	 * Creates and sends a request to get place with detailed information.
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 */
	fun getPlaceDetailed(id: String): Place? {
		val request = sygicTravelApiClient.getPlaceDetailed(id)
		return request.execute().body()?.getPlace()
	}

	/**
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 */
	fun getPlacesDetailed(ids: List<String>): List<Place>? {
		val queryIds = ids.joinToString(PlacesQuery.Operator.OR.operator)
		val request = sygicTravelApiClient.getPlacesDetailed(queryIds)
		return request.execute().body()?.getPlaces()
	}

	/**
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 */
	fun getPlaceMedia(id: String): List<Medium>? {
		val request = sygicTravelApiClient.getPlaceMedia(id)
		return request.execute().body()?.getMedia()
	}
}
