package com.sygic.travel.sdk.api.model.place

import com.google.gson.annotations.SerializedName

internal class Price {

    @SerializedName("value")
    var value: Float = 0.toFloat()

    @SerializedName("savings")
    var savings: Float = 0.toFloat()

}
