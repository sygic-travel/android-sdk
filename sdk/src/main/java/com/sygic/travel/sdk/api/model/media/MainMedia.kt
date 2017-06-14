package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.media.Medium
import com.sygic.travel.sdk.model.media.Usage

/**
 * Place main media.
 */
internal class MainMedia {

    @SerializedName("usage")
    val usage: Usage? = null

    @SerializedName("media")
    val media: List<Medium>? = null

}
