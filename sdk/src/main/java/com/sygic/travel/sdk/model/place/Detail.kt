package com.sygic.travel.sdk.model.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.media.MainMedia

/**
 * Place detailed information.
 */
class Detail {
    @SerializedName("tags")
    var tags: List<Tag>? = null

    @SerializedName("description")
    var description: Description? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("admission")
    var admission: String? = null

    @SerializedName("duration")
    var duration: Int = 0

    @SerializedName("email")
    var email: String? = null

    @SerializedName("opening_hours")
    var openingHours: String? = null

    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("main_media")
    var mainMedia: MainMedia? = null

    @SerializedName("references")
    var references: List<Reference>? = null

}
