package com.sygic.travel.sdk.places.service

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.checkedExecute
import com.sygic.travel.sdk.places.api.model.ApiCreateReviewRequest
import com.sygic.travel.sdk.places.api.model.ApiUpdateReviewVoteRequest
import com.sygic.travel.sdk.places.model.Review
import com.sygic.travel.sdk.places.model.ReviewList

internal class PlacesReviewsService(
	private val sygicTravelApiClient: SygicTravelApiClient
) {
	fun getReviews(placeId: String): ReviewList {
		val request = sygicTravelApiClient.getReviews(placeId)
		return request.checkedExecute().body()!!.data!!.fromApi()
	}

	fun createReview(placeId: String, rating: Int, message: String?) {
		sygicTravelApiClient.createReview(
			ApiCreateReviewRequest(
				rating = rating,
				place_id = placeId,
				message = message
			)
		).checkedExecute()
	}

	fun deleteReview(reviewId: Int) {
		sygicTravelApiClient.deleteReview(reviewId).checkedExecute()
	}

	fun updateReviewVote(reviewId: Int, vote: Int) {
		sygicTravelApiClient.updateReviewVote(reviewId, ApiUpdateReviewVoteRequest(vote)).checkedExecute()
	}
}
