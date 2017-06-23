package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.media.Source

/**
 * Medium's source.
 */
internal class Source {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("external_id")
    var externalId: String? = null

    @SerializedName("provider")
    var provider: String? = null

    fun convert(): Source {
        val source = Source()

        source.name = name
        source.externalId = externalId
        source.provider = provider

        return source
    }

}
