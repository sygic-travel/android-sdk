package com.sygic.travel.sdk.common.api

import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.directions.api.model.ApiDirectionRequest
import com.sygic.travel.sdk.directions.api.model.ApiDirectionsResponse
import com.sygic.travel.sdk.favorites.api.model.FavoriteRequest
import com.sygic.travel.sdk.places.api.model.ApiCreateReviewRequest
import com.sygic.travel.sdk.places.api.model.ApiGetReviewsResponse
import com.sygic.travel.sdk.places.api.model.ApiPlaceMediaResponse
import com.sygic.travel.sdk.places.api.model.ApiPlaceResponse
import com.sygic.travel.sdk.places.api.model.ApiPlacesListResponse
import com.sygic.travel.sdk.places.api.model.ApiPlacesResponse
import com.sygic.travel.sdk.places.api.model.ApiUpdateReviewVoteRequest
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

	@Headers(TIMEOUT_HEADER_PREFIX + TIMEOUT_QUICK)
	@GET("places/list")
	fun getPlaces(
		@Query("query") query: String?,
		@Query("levels") levels: String?,
		@Query("categories") categories: String?,
		@Query("categories_not") categoriesNot: String?,
		@Query("map_tiles") mapTiles: String?,
		@Query("map_spread") mapSpread: Int?,
		@Query("bounds") bounds: String?,
		@Query("preferred_location") preferredLocation: String?,
		@Query("tags") tags: String?,
		@Query("tags_not") tagsNot: String?,
		@Query("parents") parents: String?,
		@Query("hotel_star_rating") hotelStarRating: String?,
		@Query("customer_rating") customerRating: String?,
		@Query("limit") limit: Int?
	): Call<ApiResponse<ApiPlacesListResponse>>

	@Headers(TIMEOUT_HEADER_PREFIX + TIMEOUT_QUICK)
	@GET("places/{id}")
	fun getPlaceDetailed(
		@Path("id") id: String
	): Call<ApiResponse<ApiPlaceResponse>>

	@Headers(TIMEOUT_HEADER_PREFIX + TIMEOUT_BATCH)
	@GET("places")
	fun getPlacesDetailed(
		@Query("ids") ids: String
	): Call<ApiResponse<ApiPlacesResponse>>

	@GET("places/{id}/media")
	fun getPlaceMedia(
		@Path("id") id: String
	): Call<ApiResponse<ApiPlaceMediaResponse>>

	@GET("places/detect-parents")
	fun getPlacesDetectParents(
		@Query("location") location: String
	): Call<ApiResponse<ApiPlacesListResponse>>

	@GET("places/detect-parents/main-in-bounds")
	fun getPlacesDetectParentsMainInBounds(
		@Query("bounds") bounds: String?,
		@Query("map_tile_bounds") tiles: String?
	): Call<ApiResponse<ApiPlacesListResponse>>


	// ==== REVIEWS ================================================================================

	@GET("places/{place_id}/reviews")
	@Headers("Authorization: [toIntercept]")
	fun getReviews(
		@Path("place_id") placeId: String,
		@Query("limit") limit: Int = 40
	): Call<ApiResponse<ApiGetReviewsResponse>>

	@POST("reviews")
	@Headers("Authorization: [toIntercept]")
	fun createReview(
		@Body reviewRequest: ApiCreateReviewRequest
	): Call<Void>

	@DELETE("reviews/{review_id}")
	@Headers("Authorization: [toIntercept]")
	fun deleteReview(
		@Path("review_id") reviewId: Int
	): Call<Void>

	@PUT("reviews/{review_id}/votes")
	@Headers("Authorization: [toIntercept]")
	fun updateReviewVote(
		@Path("review_id") reviewId: Int,
		@Body reviewVote: ApiUpdateReviewVoteRequest
	): Call<Void>
	

	// ==== SYNCHRONIZATION=========================================================================

	@GET("changes")
	@Headers(
		"Authorization: [toIntercept]",
		TIMEOUT_HEADER_PREFIX + TIMEOUT_CHANGES
	)
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
	@Headers(
		"Authorization: [toIntercept]",
		TIMEOUT_HEADER_PREFIX + TIMEOUT_BATCH
	)
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
