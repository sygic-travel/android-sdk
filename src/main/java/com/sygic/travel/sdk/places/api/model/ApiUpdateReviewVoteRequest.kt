package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class ApiUpdateReviewVoteRequest(
	val value: Int
)
