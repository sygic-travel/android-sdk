package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.media.Attribution

/**
 * Attribution of a medium, contains information about author, license and title.
 */
internal class Attribution {

    @SerializedName("author")
    var author: String? = null

    @SerializedName("author_url")
    var authorUrl: String? = null

    @SerializedName("license")
    var license: String? = null

    @SerializedName("license_url")
    var licenseUrl: String? = null

    @SerializedName("other")
    var other: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("title_url")
    var titleUrl: String? = null
    
    fun convert(): Attribution {
        val attribution = Attribution()

        attribution.author = author
        attribution.authorUrl = authorUrl
        attribution.license = license
        attribution.licenseUrl = licenseUrl
        attribution.other = other
        attribution.title = title
        attribution.titleUrl = titleUrl

        return Attribution()
    }

}
