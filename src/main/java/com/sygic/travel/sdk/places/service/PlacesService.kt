package com.sygic.travel.sdk.places.service

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.places.facade.PlacesQuery
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.PlaceInfo
import com.sygic.travel.sdk.places.model.media.Medium

internal class PlacesService(
	private val sygicTravelApiClient: SygicTravelApiClient
) {
	fun getPlaces(
		placesQuery: PlacesQuery
	): List<PlaceInfo>? {
		val request = sygicTravelApiClient.getPlaces(
			query = placesQuery.query,
			levels = placesQuery.getLevelsApiQuery(),
			categories = placesQuery.getCategoriesApiQuery(),
			mapTiles = placesQuery.getMapTilesApiQuery(),
			mapSpread = placesQuery.mapSpread,
			bounds = placesQuery.bounds?.toApiQuery(),
			tags = placesQuery.getTagsApiQuery(),
			parents = placesQuery.getParentsApiQuery(),
			limit = placesQuery.limit
		)
		val response = request.execute()
		return response.body()?.data?.getPlaces()
	}

	fun getPlaceDetailed(id: String): Place? {
		val request = sygicTravelApiClient.getPlaceDetailed(id)
		return request.execute().body()?.data?.getPlace()
	}

	fun getPlacesDetailed(ids: List<String>): List<Place>? {
		val queryIds = ids.joinToString(PlacesQuery.LogicOperator.OR.apiOperator)
		val request = sygicTravelApiClient.getPlacesDetailed(queryIds)
		return request.execute().body()?.data?.getPlaces()
	}

	fun getPlaceMedia(id: String): List<Medium>? {
		val request = sygicTravelApiClient.getPlaceMedia(id)
		return request.execute().body()?.data?.getMedia()
	}
}
