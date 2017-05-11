package com.sygic.travel.sdk.contentProvider.api;

import com.sygic.travel.sdk.model.api.MediaResponse;
import com.sygic.travel.sdk.model.api.PlaceResponse;
import com.sygic.travel.sdk.model.api.PlacesBasicResponse;
import com.sygic.travel.sdk.model.api.PlacesResponse;
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

	/*-----------------------------------------------------------------------------*/
	/*                                     GET                                     */
	/*-----------------------------------------------------------------------------*/

	@Headers(CONTENT_TYPE_JSON)
	@GET("places/list")
	Observable<Result<PlacesBasicResponse>> getPlacesBasic(
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
	@GET("places/{id}")
	Observable<Result<PlaceResponse>> getPlace(
		@Path("id") String id
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("places/{ids}")
	Observable<Result<PlacesResponse>> getPlaces(
		@Path(encoded = true, value = "ids") String ids
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("places/{id}/media")
	Observable<Result<MediaResponse>> getPlaceMedia(
		@Path("id") String id
	);
}
