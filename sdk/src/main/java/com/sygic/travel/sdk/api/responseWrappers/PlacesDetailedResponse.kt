package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.place.Place

/**
 * Response containing a list of detailed places data. Suitable for showing details in a pager or
 * synchronization.
 */
class PlacesDetailedResponse : StResponse() {
    private var data: Data? = null

    fun getPlaces(): List<Place>? {
        return data?.places
    }

    inner class Data {
        @SerializedName("places")
        var places: List<Place>? = null
    }
}
