package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.media.Medium

/**
 * Response containing a list of place media. Suitable for a gallery.
 */
class MediaResponse : StResponse() {
    private var data: Data? = null

    fun getMedia(): List<Medium>? {
        return data?.media
    }

    inner class Data {
        @SerializedName("media")
        var media: List<Medium>? = null
    }
}
