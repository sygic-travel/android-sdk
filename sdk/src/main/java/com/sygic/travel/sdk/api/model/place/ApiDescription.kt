package com.sygic.travel.sdk.api.model.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.converter.ApiModel
import com.sygic.travel.sdk.model.place.Description

/**
 * Place description data.
 */
internal class ApiDescription : ApiModel<Description> {

    @SerializedName("text")
    var text: String? = null

    @SerializedName("provider")
    var provider: String? = null

    @SerializedName("translation_provider")
    var translationProvider: String? = null

    @SerializedName("url")
    var url: String? = null

    override fun convert(): Description {
        val description = Description()

        description.text = text
        description.provider = provider
        description.translationProvider = translationProvider
        description.url = url

        return description
    }

}
