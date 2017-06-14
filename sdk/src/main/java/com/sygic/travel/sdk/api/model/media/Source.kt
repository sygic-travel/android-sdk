package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName

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

}
