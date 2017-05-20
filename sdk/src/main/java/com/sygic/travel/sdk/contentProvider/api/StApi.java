package com.sygic.travel.sdk.contentProvider.api;

import com.sygic.travel.sdk.model.api.MediaResponse;
import com.sygic.travel.sdk.model.api.PlaceDetailedResponse;
import com.sygic.travel.sdk.model.api.PlacesResponse;
import com.sygic.travel.sdk.model.api.PlacesDetailedResponse;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <p>Contains available API requests.</p>
 * @see <a href="http://alpha-docs.sygictravelapi.com/0.1">API Documentation</a>
 */
public interface StApi {

	/*-----------------------------------------------------------------------------*/
	/*                                     GET                                     */
	/*-----------------------------------------------------------------------------*/

	@GET("places/list")
	Observable<Result<PlacesResponse>> getPlaces(
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

	@GET("places/{id}")
	Observable<Result<PlaceDetailedResponse>> getPlaceDetailed(
		@Path("id") String id
	);

	@GET("places/{ids}")
	Observable<Result<PlacesDetailedResponse>> getPlacesDetailed(
		@Path(encoded = true, value = "ids") String ids
	);

	@GET("places/{id}/media")
	Observable<Result<MediaResponse>> getPlaceMedia(
		@Path("id") String id
	);
}
