package com.sygic.travel.sdk.model.geo

import com.google.gson.annotations.SerializedName

/**
 * Geographic location - latitude, longitude.
 */
class Location {

    @SerializedName("lat")
    var lat: Float = 0.toFloat()

    @SerializedName("lng")
    var lng: Float = 0.toFloat()

}
