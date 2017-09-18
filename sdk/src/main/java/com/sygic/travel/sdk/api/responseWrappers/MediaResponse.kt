package com.sygic.travel.sdk.api.responseWrappers

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.media.ApiMedium
import com.sygic.travel.sdk.model.media.Medium

/**
 * Response containing a list of place media. Suitable for a gallery.
 */
internal class MediaResponse : StResponse() {
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
		var apiMedia: List<ApiMedium>? = null
	}
}
