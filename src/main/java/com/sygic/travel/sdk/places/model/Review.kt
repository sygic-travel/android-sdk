package com.sygic.travel.sdk.places.model

import org.threeten.bp.Instant

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
	val createdAt: Instant,
	val updatedAt: Instant?
)
