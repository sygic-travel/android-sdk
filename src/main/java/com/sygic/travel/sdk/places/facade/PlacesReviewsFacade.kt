package com.sygic.travel.sdk.places.facade

import com.sygic.travel.sdk.places.model.Review
import com.sygic.travel.sdk.places.model.ReviewList
import com.sygic.travel.sdk.places.service.PlacesReviewsService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

@Suppress("MemberVisibilityCanBePrivate", "unused")
class PlacesReviewsFacade internal constructor(
	private val reviewsService: PlacesReviewsService
) {
	fun getReviews(placeId: String): ReviewList {
		checkNotRunningOnMainThread()
		return reviewsService.getReviews(placeId)
	}

	fun createReview(placeId: String, rating: Int, message : String?) {
		checkNotRunningOnMainThread()
		reviewsService.createReview(placeId, rating, message)
	}

	fun deleteReview(reviewId: Int) {
		checkNotRunningOnMainThread()
		reviewsService.deleteReview((reviewId))
	}

	fun updateReviewVote(reviewId: Int, vote: Int) {
		checkNotRunningOnMainThread()
		reviewsService.updateReviewVote(reviewId, vote)
	}
}
