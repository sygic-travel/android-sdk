package com.sygic.travel.sdk.contentProvider.api;

import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Place;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.sygic.travel.sdk.contentProvider.api.ApiConstants.CONTENT_TYPE_JSON;

public interface StApi {

	/*******************************************************************************/
	/*                                     GET                                     */
	/*******************************************************************************/

	@Headers(CONTENT_TYPE_JSON)
	@GET("places")
	Call<List<Place>> getPlaces(
		@Query("query") String query,
		@Query("level") String level,
		@Query("categories") String categories,
		@Query("map_tile") String MapTile,
		@Query("map_spread") Integer MapSpread,
		@Query("bounds") String bounds,
		@Query("tags") String tags,
		@Query("parent") String parent,
		@Query("limit") Integer limit
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("place-details/{place_guid}")
	Call<Detail> getPlaceDetailed(
		@Path("place_guid") String placeGuid
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("places/{place_guid}/media")
	Call<List<Place>> getPlaceMedia(
		@Path("place_guid") String placeGuid
	);
}
