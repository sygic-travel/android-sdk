package com.sygic.travel.sdk.favorites.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class FavoriteRequest(
	val place_id: String
)
