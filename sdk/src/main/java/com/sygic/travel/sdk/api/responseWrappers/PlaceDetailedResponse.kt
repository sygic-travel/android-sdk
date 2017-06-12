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
        val detail = Detail()
        val place = Place()

        val dataPlace = data?.place
        dataPlace?.let {
            detail.tags = dataPlace.tags
            detail.description = dataPlace.description
            detail.address = dataPlace.address
            detail.admission = dataPlace.admission
            detail.duration = dataPlace.duration
            detail.email = dataPlace.email
            detail.openingHours = dataPlace.openingHours
            detail.phone = dataPlace.phone
            detail.mainMedia = dataPlace.mainMedia
            detail.references = dataPlace.references

            place.id = dataPlace.id
            place.level = dataPlace.level
            place.categories = dataPlace.categories
            place.rating = dataPlace.rating
            place.quadkey = dataPlace.quadkey
            place.location = dataPlace.location
            place.bounds = dataPlace.bounds
            place.name = dataPlace.name
            place.nameSuffix = dataPlace.nameSuffix
            place.perex = dataPlace.perex
            place.url = dataPlace.url
            place.thumbnailUrl = dataPlace.thumbnailUrl
            place.marker = dataPlace.marker
            place.price = dataPlace.price
            place.parentIds = dataPlace.parentIds
            place.detail = detail
        }
        return place
    }

    inner class Data {
        @SerializedName("place")
        var place: PlaceDetail? = null
    }
}
