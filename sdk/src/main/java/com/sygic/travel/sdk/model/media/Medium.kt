package com.sygic.travel.sdk.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.geo.Location

/**
 * Place medium.
 */

class Medium {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("url_template")
    var urlTemplate: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("original")
    var original: Original? = null

    @SerializedName("suitability")
    var suitability: List<String>? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("source")
    var source: Source? = null

    @SerializedName("created_by")
    var createdBy: String? = null

    @SerializedName("quadkey")
    var quadkey: String? = null

    @SerializedName("attribution")
    var attribution: Attribution? = null

    @SerializedName("location")
    var location: Location? = null

}
