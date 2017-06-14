package com.sygic.travel.sdk.api.model.place

import com.google.gson.annotations.SerializedName

internal class Tag {

    @SerializedName("key")
    var key: String? = null

    @SerializedName("name")
    var name: String? = null
}
