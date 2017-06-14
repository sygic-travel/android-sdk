package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.media.Medium

/**
 * Response containing a list of place media. Suitable for a gallery.
 */
internal class MediaResponse : StResponse() {
    private var data: Data? = null

    fun getMedia(): List<com.sygic.travel.sdk.model.media.Medium>? {
        val media = data?.media
        val convertedMedia: MutableList<com.sygic.travel.sdk.model.media.Medium> = mutableListOf()

        media?.mapTo(convertedMedia) {
            it.convert()
        }

        return convertedMedia
    }

    inner class Data {
        @SerializedName("media")
        var media: List<Medium>? = null
    }
}
