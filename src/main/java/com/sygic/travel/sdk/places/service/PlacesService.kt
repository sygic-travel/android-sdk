package com.sygic.travel.sdk.places.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.sygic.travel.sdk.common.LogicalOperator
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.checkedExecute
import com.sygic.travel.sdk.places.api.model.ApiPlaceItemResponse
import com.sygic.travel.sdk.places.api.model.ApiPlaceListItemResponse
import com.sygic.travel.sdk.places.facade.PlacesQuery
import com.sygic.travel.sdk.places.model.DetailedPlace
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import com.sygic.travel.sdk.places.model.media.Medium
import okio.buffer
import okio.source
import java.io.InputStream

internal class PlacesService constructor(
	private val sygicTravelApiClient: SygicTravelApiClient,
	private val moshi: Moshi
) {
	fun getPlaces(
		placesQuery: PlacesQuery
	): List<Place> {
		val request = sygicTravelApiClient.getPlaces(
			query = placesQuery.query,
			levels = placesQuery.getLevelsApiQuery(),
			categories = placesQuery.getCategoriesApiQuery(),
			categoriesNot = placesQuery.getCategoriesNotApiQuery(),
			mapTiles = placesQuery.getMapTilesApiQuery(),
			mapSpread = placesQuery.mapSpread,
			bounds = placesQuery.bounds?.toApiExpression(),
			preferredLocation = placesQuery.preferredLocation?.toApiExpression(),
			tags = placesQuery.getTagsApiQuery(),
			tagsNot = placesQuery.getTagsNotApiQuery(),
			parents = placesQuery.getParentsApiQuery(),
			starRating = placesQuery.getStarRatingApiQuery(),
			customerRating = placesQuery.getCustomerRatingApiQuery(),
			limit = placesQuery.limit
		)
		val response = request.checkedExecute()
		return response.body()!!.data!!.fromApi()
	}

	fun getPlaceDetailed(id: String): DetailedPlace {
		val request = sygicTravelApiClient.getPlaceDetailed(id)
		return request.checkedExecute().body()!!.data!!.fromApi()
	}

	fun getPlacesDetailed(ids: List<String>): List<DetailedPlace> {
		val queryIds = ids.joinToString(LogicalOperator.ANY.apiExpression)
		val request = sygicTravelApiClient.getPlacesDetailed(queryIds)
		return request.checkedExecute().body()!!.data!!.fromApi()
	}

	fun getPlaceMedia(id: String): List<Medium> {
		val request = sygicTravelApiClient.getPlaceMedia(id)
		return request.checkedExecute().body()!!.data!!.fromApi()
	}

	fun detectPlacesForLocation(location: LatLng): List<Place> {
		val request = sygicTravelApiClient.getPlacesDetectParents(location.toApiExpression())
		return request.checkedExecute().body()!!.data!!.fromApi()
	}

	fun detectPlacesForLocation(location: LatLngBounds): List<Place> {
		val request = sygicTravelApiClient.getPlacesDetectParentsMainInBounds(
			bounds = location.toApiExpression(),
			tiles = null
		)
		return request.checkedExecute().body()!!.data!!.fromApi()
	}

	fun parsePlacesList(json: InputStream): List<Place> {
		val input = json.source().buffer()
		val apiPlaceType = Types.newParameterizedType(List::class.java, ApiPlaceListItemResponse::class.java)
		return moshi.adapter<List<ApiPlaceListItemResponse>>(apiPlaceType).fromJson(input)!!.map { it.fromApi() }
	}

	fun parseDetailedPlacesList(json: InputStream): List<DetailedPlace> {
		val input = json.source().buffer()
		val apiDetailedPlaceType = Types.newParameterizedType(List::class.java, ApiPlaceItemResponse::class.java)
		return moshi.adapter<List<ApiPlaceItemResponse>>(apiDetailedPlaceType).fromJson(input)!!.map { it.fromApi() }
	}
}
