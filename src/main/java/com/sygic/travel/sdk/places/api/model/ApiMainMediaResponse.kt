package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class ApiMainMediaResponse(
	val usage: Usage,
	val media: List<ApiMediumResponse>
) {
	@JsonClass(generateAdapter = true)
	internal class Usage(
		val square: String,
		val landscape: String,
		val portrait: String,
		val video_preview: String?
	)
}
