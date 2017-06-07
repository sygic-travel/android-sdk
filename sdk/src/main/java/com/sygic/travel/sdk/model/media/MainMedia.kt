package com.sygic.travel.sdk.model.media

import com.google.gson.annotations.SerializedName

/**
 * Place main media.
 */
class MainMedia {

    @SerializedName("usage")
    val usage: Usage? = null

    @SerializedName("media")
    val media: List<Medium>? = null

}
