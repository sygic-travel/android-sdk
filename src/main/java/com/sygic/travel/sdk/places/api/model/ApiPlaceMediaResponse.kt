package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.media.Medium

internal class ApiPlaceMediaResponse(
	private var media: List<ApiMediumResponse>
) {
	fun getMedia(): List<Medium> {
		return media.map { it.fromApi() }
	}
}
