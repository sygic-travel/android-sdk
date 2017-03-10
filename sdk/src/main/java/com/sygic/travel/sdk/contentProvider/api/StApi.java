package com.sygic.travel.sdk.contentProvider.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.sygic.travel.sdk.contentProvider.api.ApiClient.CONTENT_TYPE_JSON;

/**
 * Created by michal.murin on 16.2.2017.
 */

public interface StApi {

	/*******************************************************************************/
	/*                                     GET                                     */
	/*******************************************************************************/

	@Headers(CONTENT_TYPE_JSON)
	@GET("{apiKey}/items/{guid}")
	Call<JsonElement> getItem(
		@Path("apiKey") String apiKey,
		@Path("guid") String guid
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("{apiKey}/items/")
	Call<JsonElement> getItems(
		@Path("apiKey") String apiKey,
		@Query("guid") String guid
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("{apiKey}/locations/search")
	Call<JsonElement> getLocation(
		@Path("apiKey") String apiKey,
		@Query("q") String query
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("{apiKey}/destinations/search")
	Call<JsonElement> getDestination(
		@Path("apiKey") String apiKey,
		@Query("lat") float lat,
		@Query("lng") float lng
	);

	@GET("{apiKey}/exchange-rates")
	Call<JsonElement> getExchangeRates(
		@Path("apiKey") String apiKey
	);

	@GET("{apiVersion}/{apiKey}/media/{guid}")
	Call<JsonElement> getMedia(
		@Path("apiVersion") String apiVersion,
		@Path("apiKey") String apiKey,
		@Path("guid") String guid
	);

	/*******************************************************************************/
	/*                                     CDN                                     */
	/*******************************************************************************/

	@Headers(CONTENT_TYPE_JSON)
	@GET("features/")
	Call<JsonElement> getFeaturesForList(
		@Query("type") String type,
		@Query("tags") String tags,
		@Query("limit") String limit,
		@Query("categories") String categories,
		@Query("customer_rating") String customerRating,
		@Query("star_rating.value") String starRating,
		@Query("price.value") String price,
		@Query("parent") String parent
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("features/")
	Call<JsonElement> getFeaturesForMap(
		@Query("type") String type,
		@Query("map_tile") String map_tile,
		@Query("map_spread") String map_spread,
		@Query("tags") String tags,
		@Query("limit") String limit,
		@Query("categories") String categories,
		@Query("customer_rating") String customerRating,
		@Query("star_rating.value") String starRating,
		@Query("price.value") String price
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("features/")
	Call<JsonElement> getFeaturesForSearch(
		@Query("type") String type,
		@Query("query") String query,
		@Query("location") String location,
		@Query("limit") int limit,
		@Query("parent") String parent
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("features-stats/")
	Call<JsonElement> getFeatureStats(
		@Query("map_tile_bounds") String map_tile,
		@Query("parent") String parent,
		@Query("type") String type,
		@Query("tags") String tags,
		@Query("categories") String categories,
		@Query("customer_rating") String customerRating,
		@Query("star_rating.value") String starRating,
		@Query("price.value") String price
	);

	@Headers(CONTENT_TYPE_JSON)
	@GET("items/{guid}")
	Call<JsonElement> getItem(
		@Path("guid") String guid
	);
}
