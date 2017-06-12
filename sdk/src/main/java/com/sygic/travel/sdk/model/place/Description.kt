package com.sygic.travel.sdk.model.place

import com.google.gson.annotations.SerializedName

/**
 *
 * Place description data.
 */
class Description {

    @SerializedName("text")
    var text: String? = null

    @SerializedName("provider")
    var provider: String? = null

    @SerializedName("translation_provider")
    var translationProvider: String? = null

    @SerializedName("url")
    var url: String? = null

}
