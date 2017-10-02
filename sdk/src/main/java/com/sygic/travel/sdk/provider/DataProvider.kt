package com.sygic.travel.sdk.provider

import com.sygic.travel.sdk.api.StApi
import com.sygic.travel.sdk.db.StDb
import com.sygic.travel.sdk.model.media.Medium
import com.sygic.travel.sdk.model.place.Favorite
import com.sygic.travel.sdk.model.place.Place
import com.sygic.travel.sdk.model.place.Tour
import com.sygic.travel.sdk.model.query.PlacesQuery
import com.sygic.travel.sdk.model.query.ToursQuery


/**
 * Data provider contains methods for fetching data either from API or a database.
 */
internal class DataProvider(
	private val stApi: StApi,
	private val stDb: StDb
) {
	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param placesQuery PlacesQuery encapsulating data for API request.
	 */
	fun getPlaces(
		placesQuery: PlacesQuery
	): List<Place>? {
		val request = stApi.getPlaces(
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
		val request = stApi.getPlaceDetailed(id)
		return request.execute().body()?.getPlace()
	}


	/**
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 */
	fun getPlacesDetailed(ids: List<String>): List<Place>? {
		val queryIds = ids.joinToString(PlacesQuery.Operator.OR.operator)
		val request = stApi.getPlacesDetailed(queryIds)
		return request.execute().body()?.getPlaces()
	}


	/**
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 */
	fun getPlaceMedia(id: String): List<Medium>? {
		val request = stApi.getPlaceMedia(id)
		return request.execute().body()?.getMedia()
	}


	/**
	 * Creates and sends a request to get the Tours.
	 * @param toursQuery ToursQuery encapsulating data for API request.
	 */
	fun getTours(toursQuery: ToursQuery): List<Tour>? {
		val request = stApi.getTours(
			destinationId = toursQuery.destinationId,
			page = toursQuery.page,
			sortBy = toursQuery.sortBy?.string,
			sortDirection = toursQuery.sortDirection?.string
		)
		return request.execute().body()?.getTours()
	}


	/**
	 * Stores a place's id in a local persistent storage. The place is added to the favorites.
	 * @param id A place's id, which is stored.
	 */
	fun addPlaceToFavorites(id: String) {
		val favorite = Favorite()
		favorite.id = id
		stDb.favoriteDao().insert(favorite)
	}


	/**
	 * Removes a place's id from a local persistent storage. The place is removed from the favorites.
	 * @param id A place's id, which is removed.
	 */
	fun removePlaceFromFavorites(id: String) {
		val favorite = Favorite()
		favorite.id = id
		stDb.favoriteDao().delete(favorite)
	}


	/**
	 * Method returns a list of all favorite places' ids.
	 */
	fun getFavoritesIds(): List<String> {
		val favorites = stDb.favoriteDao().loadAll()
		return favorites.map { it.id!! }
	}
}
