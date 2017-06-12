package com.sygic.travel.sdk.model.geo

import com.google.gson.annotations.SerializedName

/**
 *
 * Geographical bounds - south, west, north, east.
 */
class Bounds {

    @SerializedName("north")
    var north: Float = 0.toFloat()

    @SerializedName("east")
    var east: Float = 0.toFloat()

    @SerializedName("south")
    var south: Float = 0.toFloat()

    @SerializedName("west")
    var west: Float = 0.toFloat()

    fun toQueryString(): String {
        return south.toString() + "," + west + "," + north + "," + east
    }
}
