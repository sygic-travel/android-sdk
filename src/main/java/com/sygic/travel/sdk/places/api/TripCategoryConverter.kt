package com.sygic.travel.sdk.places.api

import com.sygic.travel.sdk.places.model.Category

internal object TripCategoryConverter {
	private const val CATEGORY_DISCOVERING = "discovering"
	private const val CATEGORY_EATING = "eating"
	private const val CATEGORY_GOING_OUT = "going_out"
	private const val CATEGORY_HIKING = "hiking"
	private const val CATEGORY_PLAYING = "playing"
	private const val CATEGORY_RELAXING = "relaxing"
	private const val CATEGORY_SHOPPING = "shopping"
	private const val CATEGORY_SIGHTSEEING = "sightseeing"
	private const val CATEGORY_SLEEPING = "sleeping"
	private const val CATEGORY_DOING_SPORTS = "doing_sports"
	private const val CATEGORY_TRAVELING = "traveling"

	fun fromApiCategories(category: String): Category? {
		return when (category) {
			CATEGORY_DISCOVERING -> Category.DISCOVERING
			CATEGORY_EATING -> Category.EATING
			CATEGORY_GOING_OUT -> Category.GOING_OUT
			CATEGORY_HIKING -> Category.HIKING
			CATEGORY_PLAYING -> Category.PLAYING
			CATEGORY_RELAXING -> Category.RELAXING
			CATEGORY_SHOPPING -> Category.SHOPPING
			CATEGORY_SIGHTSEEING -> Category.SIGHTSEEING
			CATEGORY_SLEEPING -> Category.SLEEPING
			CATEGORY_DOING_SPORTS -> Category.DOING_SPORTS
			CATEGORY_TRAVELING -> Category.TRAVELING
			else -> null
		}
	}
}
