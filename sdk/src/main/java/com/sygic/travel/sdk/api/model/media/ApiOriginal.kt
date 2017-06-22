package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.converter.ApiModel
import com.sygic.travel.sdk.model.media.Original

/**
 * Medium's original parameters.
 */
internal class ApiOriginal : ApiModel<Original> {

    @SerializedName("size")
    var size: Int = 0

    @SerializedName("width")
    var width: Int = 0

    @SerializedName("height")
    var height: Int = 0

    override fun convert(): Original {
        val original = Original()

        original.size = size
        original.width = width
        original.height = height

        return original
    }

}
