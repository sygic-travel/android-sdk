package com.sygic.travel.sdk.places.model

import java.util.Date

@Suppress("unused")
class Review(
	val id: Int,
	val userId: String,
	val userName: String,
	val placeId: String,
	val message: String?,
	val rating: Int?,
	val votesUp: Int,
	val votesDown: Int,
	val votesScore: Int,
	val currentUserVote: Int,
	val createdAt: Date,
	val updatedAt: Date?
)
