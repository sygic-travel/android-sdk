package com.sygic.travel.sdk.model.place

import com.google.gson.annotations.SerializedName

/**
 * Model class for Tour.
 */
class Tour {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("perex")
    var perex: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("rating")
    var rating: Float? = null

    @SerializedName("review_count")
    var reviewCount: Int? = null

    @SerializedName("photo_url")
    var photoUrl: String? = null

    @SerializedName("price")
    var price: Float? = null

    @SerializedName("original_price")
    var originalPrice: Float? = null

    @SerializedName("duration")
    var duration: String? = null
}
