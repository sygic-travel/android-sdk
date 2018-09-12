package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class ApiCreateReviewRequest(
	val rating: Int,
	val place_id: String,
	val message: String?
)
