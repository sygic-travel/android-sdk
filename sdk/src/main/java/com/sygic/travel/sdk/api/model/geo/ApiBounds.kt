package com.sygic.travel.sdk.api.model.geo

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.converter.ApiModel
import com.sygic.travel.sdk.model.geo.Bounds

/**
 * Geographical bounds - south, west, north, east.
 */
internal class ApiBounds : ApiModel<Bounds> {

    @SerializedName("north")
    var north: Float = 0.toFloat()

    @SerializedName("east")
    var east: Float = 0.toFloat()

    @SerializedName("south")
    var south: Float = 0.toFloat()

    @SerializedName("west")
    var west: Float = 0.toFloat()

    override fun convert(): Bounds {
        val bounds = Bounds()

        bounds.north = north
        bounds.east = east
        bounds.south = south
        bounds.west = west

        return bounds
    }
}
