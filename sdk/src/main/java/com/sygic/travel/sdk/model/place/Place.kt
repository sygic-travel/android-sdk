package com.sygic.travel.sdk.model.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.geo.Bounds
import com.sygic.travel.sdk.model.geo.Location

open class Place {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("level")
    var level: String? = null

    @SerializedName("categories")
    var categories: List<String>? = null

    @SerializedName("rating")
    var rating: Float = 0.toFloat()

    @SerializedName("quadkey")
    var quadkey: String? = null

    @SerializedName("location")
    var location: Location? = null

    @SerializedName("bounding_box")
    var bounds: Bounds? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("name_suffix")
    var nameSuffix: String? = null

    @SerializedName("perex")
    var perex: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("thumbnail_url")
    var thumbnailUrl: String? = null

    @SerializedName("marker")
    var marker: String? = null

    @SerializedName("price")
    var price: Price? = null

    @SerializedName("parent_ids")
    var parentIds: List<String>? = null

    var detail: Detail? = null

    fun hasLocation(): Boolean {
        return location != null && (location!!.lat.compareTo(0.0f) != 0 || location!!.lng.compareTo(0.0f) != 0)
    }

    fun hasThumbnailUrl(): Boolean {
        return thumbnailUrl != null && thumbnailUrl != ""
    }
}
