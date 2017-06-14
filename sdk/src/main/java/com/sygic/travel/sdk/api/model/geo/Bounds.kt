package com.sygic.travel.sdk.api.model.geo

import com.google.gson.annotations.SerializedName

/**
 * Geographical bounds - south, west, north, east.
 */
internal class Bounds {
    @SerializedName("north")
    var north: Float = 0.toFloat()

    @SerializedName("east")
    var east: Float = 0.toFloat()

    @SerializedName("south")
    var south: Float = 0.toFloat()

    @SerializedName("west")
    var west: Float = 0.toFloat()
}
