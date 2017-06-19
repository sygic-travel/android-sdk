package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.place.Tour

/**
 * Response that contains a list of Tour classes.
 */
internal class TourResponse : StResponse() {
    private var data: Data? = null

    fun getTours(): List<Tour>? = data?.tours

    inner class Data {
        @SerializedName("tours")
        var tours: List<Tour>? = null
    }
}