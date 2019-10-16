package com.sygic.travel.sdk.places.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.places.model.Review
import com.sygic.travel.sdk.places.model.ReviewList
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
internal class ApiGetReviewsResponse(
	val rating: Float,
	val reviews: List<ApiReviewResponse>
) {
	@JsonClass(generateAdapter = true)
	internal class ApiReviewResponse(
		val id: Int,
		val user_id: String,
		val user_name: String,
		val place_id: String,
		val message: String?,
		val rating: Int?,
		val votes_up: Int,
		val votes_down: Int,
		val votes_score: Int,
		val current_user_vote: Int,
		val created_at: String,
		val updated_at: String?
	) {
		fun fromApi(): Review {
			return Review(
				id = id,
				userId = user_id,
				userName = user_name,
				placeId = place_id,
				message = message,
				rating = rating,
				votesUp = votes_up,
				votesDown = votes_down,
				votesScore = votes_score,
				currentUserVote = current_user_vote,
				createdAt = OffsetDateTime.parse(created_at).toInstant(),
				updatedAt = updated_at?.let { OffsetDateTime.parse(it).toInstant() }
			)
		}
	}

	fun fromApi(): ReviewList {
		return ReviewList(
			rating = rating,
			reviews = reviews.map { it.fromApi() }
		)
	}
}
