package com.sygic.travel.sdk.places.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.common.api.model.ApiResponse
import com.sygic.travel.sdk.places.model.api.media.ApiMedium
import com.sygic.travel.sdk.places.model.media.Medium

/**
 * ApiResponse containing a list of place media. Suitable for a gallery.
 */
class MediaApiResponse : ApiResponse() {
	private var data: Data? = null

	fun getMedia(): List<Medium>? {
		val media = data?.apiMedia
		val convertedMedia: MutableList<Medium> = mutableListOf()

		media?.mapTo(convertedMedia) {
			it.convert()
		}

		return convertedMedia
	}

	inner class Data {
		@SerializedName("media")
		internal var apiMedia: List<ApiMedium>? = null
	}
}
