package com.sygic.travel.sdk.api.model.geo

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.geo.Location

/**
 * Geographic location - latitude, longitude.
 */
internal class Location {

    @SerializedName("lat")
    var lat: Float = 0.toFloat()

    @SerializedName("lng")
    var lng: Float = 0.toFloat()

    fun convert(): Location {
        val location = Location()

        location.lat = lat
        location.lng = lng

        return location
    }

}
