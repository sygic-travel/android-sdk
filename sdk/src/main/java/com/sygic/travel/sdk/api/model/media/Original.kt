package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName

/**
 * Medium's original parameters.
 */
internal class Original {

    @SerializedName("size")
    var size: Int = 0

    @SerializedName("width")
    var width: Int = 0

    @SerializedName("height")
    var height: Int = 0

}
