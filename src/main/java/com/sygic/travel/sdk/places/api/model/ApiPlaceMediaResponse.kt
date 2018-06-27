package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.media.Medium

@JsonClass(generateAdapter = true)
internal class ApiPlaceMediaResponse(
	var media: List<ApiMediumResponse>
) {
	fun fromApi(): List<Medium> {
		return media.map { it.fromApi() }
	}
}
