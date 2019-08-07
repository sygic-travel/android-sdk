package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.api.TripCategoryConverter

@Suppress("unused")
enum class Category {
	DISCOVERING,
	EATING,
	GOING_OUT,
	HIKING,
	PLAYING,
	RELAXING,
	SHOPPING,
	SIGHTSEEING,
	SLEEPING,
	DOING_SPORTS,
	TRAVELING;

	fun toApiExpression(): String {
		return TripCategoryConverter.toApiCategory(this)
	}
}
