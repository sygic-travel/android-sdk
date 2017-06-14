package com.sygic.travel.sdk.model.media

import com.google.gson.annotations.SerializedName

/**
 * Place main media.
 */
class MainMedia {

    @SerializedName("usage")
    var usage: Usage? = null

    @SerializedName("media")
    var media: List<Medium>? = null

}
