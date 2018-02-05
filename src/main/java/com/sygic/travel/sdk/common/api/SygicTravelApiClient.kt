package com.sygic.travel.sdk.common.api

import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.directions.api.model.ApiDirectionRequest
import com.sygic.travel.sdk.directions.api.model.ApiDirectionsResponse
import com.sygic.travel.sdk.favorites.api.model.FavoriteRequest
import com.sygic.travel.sdk.places.api.model.ApiPlaceMediaResponse
import com.sygic.travel.sdk.places.api.model.ApiPlaceResponse
import com.sygic.travel.sdk.places.api.model.ApiPlacesListResponse
import com.sygic.travel.sdk.places.api.model.ApiPlacesResponse
import com.sygic.travel.sdk.synchronization.api.model.ApiChangesResponse
import com.sygic.travel.sdk.tours.api.model.ApiTourResponse
import com.sygic.travel.sdk.trips.api.model.ApiCloneTripRequest
import com.sygic.travel.sdk.trips.api.model.ApiCloneTripResponse
import com.sygic.travel.sdk.trips.api.model.ApiCreateTripResponse
import com.sygic.travel.sdk.trips.api.model.ApiDeleteTripsInTrashResponse
import com.sygic.travel.sdk.trips.api.model.ApiGetTripResponse
import com.sygic.travel.sdk.trips.api.model.ApiGetTripsResponse
import com.sygic.travel.sdk.trips.api.model.ApiTripItemRequest
import com.sygic.travel.sdk.trips.api.model.ApiTripsListResponse
import com.sygic.travel.sdk.trips.api.model.ApiUpdateTripResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @see [API Documentation](http://docs.sygictravelapi.com/1.0)
 */
internal interface SygicTravelApiClient {

	// ==== DIRECTIONS =============================================================================

	@POST("directions/path")
	fun getDirections(
		@Body directionsRequest: List<ApiDirectionRequest>
	): Call<ApiResponse<ApiDirectionsResponse>>


	// ==== FAVORITES ==============================================================================

	@POST("favorites")
	@Headers("Authorization: [toIntercept]")
	fun createFavorite(
		@Body request: FavoriteRequest
	): Call<Void>

	@HTTP(method = "DELETE", path = "favorites", hasBody = true)
	@Headers("Authorization: [toIntercept]")
	fun deleteFavorite(
		@Body request: FavoriteRequest
	): Call<Void>


	// ==== PLACES =================================================================================

	@GET("places/list")
	fun getPlaces(
		@Query("query") query: String?,
		@Query(encoded = true, value = "levels") levels: String?,
		@Query(encoded = true, value = "categories") categories: String?,
		@Query(encoded = true, value = "map_tiles") mapTiles: String?,
		@Query("map_spread") mapSpread: Int?,
		@Query("bounding_box") bounds: String?,
		@Query(encoded = true, value = "tags") tags: String?,
		@Query(encoded = true, value = "parents") parents: String?,
		@Query("limit") limit: Int?
	): Call<ApiResponse<ApiPlacesListResponse>>

	@GET("places/{id}")
	fun getPlaceDetailed(
		@Path("id") id: String
	): Call<ApiResponse<ApiPlaceResponse>>

	@GET("places")
	fun getPlacesDetailed(
		@Query(encoded = true, value = "ids") ids: String
	): Call<ApiResponse<ApiPlacesResponse>>

	@GET("places/{id}/media")
	fun getPlaceMedia(
		@Path("id") id: String
	): Call<ApiResponse<ApiPlaceMediaResponse>>


	// ==== SYNCHRONIZATION=========================================================================

	@GET("changes")
	@Headers("Authorization: [toIntercept]")
	fun getChanges(
		@Query("since") since: String?
	): Call<ApiResponse<ApiChangesResponse>>


	// ==== TOURS ==================================================================================

	@GET("tours/get-your-guide")
	fun getToursGetYourGuide(
		@Query("query") query: String?,
		@Query("bounds") bounds: String?,
		@Query("parent_place_id") parentPlaceId: String?,
		@Query("tags") tags: String?,
		@Query("from") from: String?,
		@Query("to") to: String?,
		@Query("duration") duration: String?,
		@Query("page") page: Int?,
		@Query("count") count: Int?,
		@Query("sort_by") sortBy: String?,
		@Query("sort_direction") sortDirection: String?
	): Call<ApiResponse<ApiTourResponse>>

	@GET("tours/viator")
	fun getToursViator(
		@Query("parent_place_id") parentPlaceId: String,
		@Query("page") page: Int?,
		@Query("sort_by") sortBy: String?,
		@Query("sort_direction") sortDirection: String?
	): Call<ApiResponse<ApiTourResponse>>

	// ==== TRIPS ==================================================================================

	@GET("trips/list")
	@Headers("Authorization: [toIntercept]")
	fun getTripList(
		@Query("from") from: String? = null,
		@Query("to") to: String? = null
	): Call<ApiResponse<ApiTripsListResponse>>

	@GET("trips")
	@Headers("Authorization: [toIntercept]")
	fun getTrips(
		@Query("ids") ids: String
	): Call<ApiResponse<ApiGetTripsResponse>>

	@POST("trips")
	@Headers("Authorization: [toIntercept]")
	fun createTrip(
		@Body trip: ApiTripItemRequest
	): Call<ApiResponse<ApiCreateTripResponse>>

	@GET("trips/{trip_id}")
	@Headers("Authorization: [toIntercept]")
	fun getTrip(
		@Path("trip_id") tripId: String
	): Call<ApiResponse<ApiGetTripResponse>>

	@PUT("trips/{trip_id}")
	@Headers("Authorization: [toIntercept]")
	fun updateTrip(
		@Path("trip_id") tripId: String,
		@Body updateRequest: ApiTripItemRequest
	): Call<ApiResponse<ApiUpdateTripResponse>>

	@POST("trips/clone")
	@Headers("Authorization: [toIntercept]")
	fun cloneTrip(
		@Body cloneRequest: ApiCloneTripRequest
	): Call<ApiResponse<ApiCloneTripResponse>>

	@DELETE("trips/trash")
	@Headers("Authorization: [toIntercept]")
	fun deleteTripsInTrash(
	): Call<ApiResponse<ApiDeleteTripsInTrashResponse>>
}
