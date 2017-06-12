package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.model.place.Detail
import com.sygic.travel.sdk.model.place.Place
import com.sygic.travel.sdk.model.place.PlaceDetail

/**
 * Response containing one detailed place data. Suitable for showing a place detail.
 */
class PlaceDetailedResponse : StResponse() {
    private val data: Data? = null

    fun getPlace(): Place? {
        if (data == null || data.place == null) return null

        val detail = Detail()
        detail.tags = data.place!!.tags
        detail.description = data.place!!.description
        detail.address = data.place!!.address
        detail.admission = data.place!!.admission
        detail.duration = data.place!!.duration
        detail.email = data.place!!.email
        detail.openingHours = data.place!!.openingHours
        detail.phone = data.place!!.phone
        detail.mainMedia = data.place!!.mainMedia
        detail.references = data.place!!.references

        val place = Place()
        place.id = data.place!!.id
        place.level = data.place!!.level
        place.categories = data.place!!.categories
        place.rating = data.place!!.rating
        place.quadkey = data.place!!.quadkey
        place.location = data.place!!.location
        place.bounds = data.place!!.bounds
        place.name = data.place!!.name
        place.nameSuffix = data.place!!.nameSuffix
        place.perex = data.place!!.perex
        place.url = data.place!!.url
        place.thumbnailUrl = data.place!!.thumbnailUrl
        place.marker = data.place!!.marker
        place.price = data.place!!.price
        place.parentIds = data.place!!.parentIds
        place.detail = detail
        return place
    }

    inner class Data {
        @SerializedName("place")
        var place: PlaceDetail? = null
    }
}
