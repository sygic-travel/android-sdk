package com.sygic.travel.sdk.favorites.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class FavoriteRequest(
	val place_id: String
)
