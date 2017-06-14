package com.sygic.travel.sdk.api

import com.sygic.travel.sdk.api.responseWrappers.MediaResponse
import com.sygic.travel.sdk.api.responseWrappers.PlaceDetailedResponse
import com.sygic.travel.sdk.api.responseWrappers.PlacesResponse
import retrofit2.adapter.rxjava.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 *
 * Contains available API requests.
 * @see [API Documentation](http://docs.sygictravelapi.com/0.2)
 */
internal interface StApi {

    /*-----------------------------------------------------------------------------*/
    /*                                     GET                                     */
    /*-----------------------------------------------------------------------------*/

    @retrofit2.http.GET("places/list")
    fun getPlaces(
            @retrofit2.http.Query("query") query: String?,
            @retrofit2.http.Query("levels") levels: String?,
            @retrofit2.http.Query("categories") categories: String?,
            @retrofit2.http.Query(encoded = true, value = "map_tiles") mapTiles: String?,
            @retrofit2.http.Query("map_spread") mapSpread: Int?,
            @retrofit2.http.Query("bounds") bounds: String?,
            @retrofit2.http.Query("tags") tags: String?,
            @retrofit2.http.Query("parents") parents: String?,
            @retrofit2.http.Query("limit") limit: Int?
    ): rx.Observable<Result<PlacesResponse>>

    @retrofit2.http.GET("places/{id}")
    fun getPlaceDetailed(
            @retrofit2.http.Path("id") id: String
    ): rx.Observable<Result<PlaceDetailedResponse>>

    @retrofit2.http.GET("places")
    fun getPlacesDetailed(
            @retrofit2.http.Query(encoded = true, value = "ids") ids: String
    ): rx.Observable<Result<PlacesResponse>>

    @retrofit2.http.GET("places/{id}/media")
    fun getPlaceMedia(
            @retrofit2.http.Path("id") id: String
    ): rx.Observable<Result<MediaResponse>>
}
