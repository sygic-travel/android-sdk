package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.model.Category
import com.sygic.travel.sdk.places.model.Level
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.geo.Bounds

open internal class ApiPlaceListItemResponse(
	val id: String,
	val level: String,
	val categories: List<String>,
	val rating: Float,
	val quadkey: String,
	val location: ApiLocationResponse,
	val bounding_box: ApiBounds?,
	val name: String,
	val name_suffix: String?,
	val perex: String?,
	val url: String?,
	val thumbnail_url: String?,
	val marker: String,
	val parent_ids: List<String>,
	val star_rating: Float?,
	val star_rating_unofficial: Float?,
	val customer_rating: Float?,
	val owner_id: String?
) {
	companion object {
		private const val LEVEL_CONTINENT = "continent"
		private const val LEVEL_COUNTRY = "country"
		private const val LEVEL_STATE = "state"
		private const val LEVEL_REGION = "region"
		private const val LEVEL_COUNTY = "county"
		private const val LEVEL_CITY = "city"
		private const val LEVEL_TOWN = "town"
		private const val LEVEL_VILLAGE = "village"
		private const val LEVEL_SETTLEMENT = "settlement"
		private const val LEVEL_LOCALITY = "locality"
		private const val LEVEL_NEIGHBOURHOOD = "neighbourhood"
		private const val LEVEL_ARCHIPELAGO = "archipelago"
		private const val LEVEL_ISLAND = "island"
		private const val LEVEL_POI = "poi"
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
	}

	class ApiBounds(
		val north: Float,
		val east: Float,
		val south: Float,
		val west: Float
	) {
		fun fromApi(): Bounds {
			return Bounds(
				north = north,
				east = east,
				south = south,
				west = west
			)
		}
	}

	open fun fromApi(): Place {
		return Place(
			id = id,
			level = fromApiLevel(level),
			categories = fromApiCategories(categories),
			rating = rating,
			quadkey = quadkey,
			location = location.fromApi(),
			boundingBox = bounding_box?.fromApi(),
			name = name,
			nameSuffix = name_suffix,
			perex = perex,
			url = url,
			thumbnailUrl = thumbnail_url,
			marker = marker,
			parentIds = parent_ids.toSet(),
			starRating = star_rating ,
			starRatingUnofficial = star_rating_unofficial,
			customerRating = customer_rating,
			ownerId = owner_id
		)
	}

	protected fun fromApiLevel(level: String): Level {
		return when (level) {
			LEVEL_CONTINENT -> Level.CONTINENT
			LEVEL_COUNTRY -> Level.COUNTRY
			LEVEL_STATE -> Level.STATE
			LEVEL_REGION -> Level.REGION
			LEVEL_COUNTY -> Level.COUNTRY
			LEVEL_CITY -> Level.CITY
			LEVEL_TOWN -> Level.TOWN
			LEVEL_VILLAGE -> Level.VILLAGE
			LEVEL_SETTLEMENT -> Level.SETTLEMENT
			LEVEL_LOCALITY -> Level.LOCALITY
			LEVEL_NEIGHBOURHOOD -> Level.NEIGHBOURHOOD
			LEVEL_ARCHIPELAGO -> Level.ARCHIPELAGO
			LEVEL_ISLAND -> Level.ISLAND
			LEVEL_POI -> Level.POI
			else -> Level.POI
		}
	}

	protected fun fromApiCategories(categories: List<String>): Set<Category> {
		return categories.mapNotNull {
			when (it) {
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
		}.toSet()
	}
}
