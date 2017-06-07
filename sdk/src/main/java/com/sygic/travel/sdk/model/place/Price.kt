package com.sygic.travel.sdk.model.place

import com.google.gson.annotations.SerializedName

class Price {

    @SerializedName("value")
    var value: Float = 0.toFloat()

    @SerializedName("savings")
    var savings: Float = 0.toFloat()

}
