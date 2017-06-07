package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.place.Place

/**
 * Response that contains a list of basic places data (without detailed data). Suitable for showing
 * places in a list or spread on a map.
 */
class PlacesResponse : StResponse() {
    private var data: Data? = null

    fun getPlaces(): List<Place>? {
        return data?.places
    }

    inner class Data {
        @SerializedName("places")
        var places: List<Place>? = null
    }
}
