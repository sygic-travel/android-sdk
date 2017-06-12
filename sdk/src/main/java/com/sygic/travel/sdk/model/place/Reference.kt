package com.sygic.travel.sdk.model.place

import com.google.gson.annotations.SerializedName

/**
 * Link (Wiki, web site, etc.) related to a specific place.
 */
class Reference {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("language_id")
    var languageId: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("supplier")
    var supplier: String? = null

    @SerializedName("priority")
    var priority: Int = 0

    @SerializedName("currency")
    var currency: String? = null

    @SerializedName("price")
    var price: Float = 0.toFloat()

    @SerializedName("flags")
    var flags: List<String>? = null

}
