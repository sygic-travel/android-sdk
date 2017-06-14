package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.media.MainMedia

/**
 * Place main media.
 */
internal class MainMedia {

    @SerializedName("usage")
    val usage: Usage? = null

    @SerializedName("media")
    val media: List<Medium>? = null

    fun convert(): MainMedia {
        val mainMedia = MainMedia()

        val convertedMedia: MutableList<com.sygic.travel.sdk.model.media.Medium> = mutableListOf()
        media?.mapTo(convertedMedia) {
            it.convert()
        }

        mainMedia.usage = usage?.convert()
        mainMedia.media = convertedMedia

        return mainMedia
    }
}
