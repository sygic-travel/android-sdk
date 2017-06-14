package com.sygic.travel.sdk.api.model.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.place.Tag

internal class Tag {

    @SerializedName("key")
    var key: String? = null

    @SerializedName("name")
    var name: String? = null

    fun convert(): Tag {
        val tag = Tag()

        tag.key = key
        tag.name = name

        return tag
    }
}
