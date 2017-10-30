package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.media.Medium

/**
 * ApiResponse containing a list of place media. Suitable for a gallery.
 */
class ApiPlaceMediaResponse(
	private var media: List<ApiMediumResponse>
) {
	fun getMedia(): List<Medium>? {
		return media.map { it.fromApi() }
	}
}
