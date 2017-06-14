package com.sygic.travel.sdk.model.media

import com.google.gson.annotations.SerializedName

/**
 * Medium's suitable usages.
 */
class Usage {

    @SerializedName("square")
    var square: String? = null

    @SerializedName("landscape")
    var landscape: String? = null

    @SerializedName("portrait")
    var portrait: String? = null

    @SerializedName("video_preview")
    var videoPreview: String? = null

}
