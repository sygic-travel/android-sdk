package com.sygic.travel.sdk.contentProvider.api;

import com.sygic.travel.sdk.model.api.StResponse;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.CONTENT_TYPE_JSON;

/**
 * <p>Contains available API requests.</p>
 * @see <a href="http://alpha-docs.sygictravelapi.com/0.1">API Documentation</a>
 */
public interface StApi {
	String PLACES_API_CALL = "places_api_call";
	String DETAIL_API_CALL = "detail_api_call";
	String MEDIA_API_CALL = "media_api_call";

	/*-----------------------------------------------------------------------------*/
	/*                                     GET                                     */
	/*-----------------------------------------------------------------------------*/

	@Headers(CONTENT_TYPE_JSON)
	@GET("places")
	Observable<Result<StResponse>> getPlaces(
		@Query("query") String query,
		@Query("levels") String levels,
		@Query("categories") String categories,
		@Query(encoded = true, value = "map_tiles") String mapTiles,
		@Query("map_spread") Integer mapSpread,
		@Query("bounds") String bounds,
		@Query("tags") String tags,
		@Query("parents") String parents,
		@Query("limit") Integer limit
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("place-details/{place_guid}")
	Observable<Result<StResponse>> getPlaceDetailed(
		@Path("place_guid") String placeGuid
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("places/{place_guid}/media")
	Observable<Result<StResponse>> getPlaceMedia(
		@Path("place_guid") String placeGuid
	);
}
