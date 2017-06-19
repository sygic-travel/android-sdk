package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.place.ApiPlaceDetail
import com.sygic.travel.sdk.model.place.*

/**
 * Response containing one detailed place data. Suitable for showing a place detail.
 */
internal class PlaceDetailedResponse : StResponse() {
    private val data: Data? = null

    fun getPlace(): Place? {
        val placeDetail = data?.apiPlace
        val place = placeDetail?.convert()

        return place
    }

    inner class Data {
        @SerializedName("place")
        var apiPlace: ApiPlaceDetail? = null
    }
}
