package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.media.Medium
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiPlaceMediaResponse(
	var media: List<ApiMediumResponse>
) {
	fun fromApi(): List<Medium> {
		return media.map { it.fromApi() }
	}
}
