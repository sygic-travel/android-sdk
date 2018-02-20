package com.sygic.travel.sdk.places.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiMainMediaResponse(
	val media: List<ApiMediumResponse>
)
