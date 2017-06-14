package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.media.Usage

/**
 * Medium's suitable usages.
 */
internal class Usage {

    @SerializedName("square")
    val square: String? = null

    @SerializedName("landscape")
    val landscape: String? = null

    @SerializedName("portrait")
    val portrait: String? = null

    @SerializedName("video_preview")
    val videoPreview: String? = null

    fun convert(): Usage {
        val usage = Usage()

        usage.square = square
        usage.landscape = landscape
        usage.portrait = portrait
        usage.videoPreview = videoPreview

        return usage
    }
}
