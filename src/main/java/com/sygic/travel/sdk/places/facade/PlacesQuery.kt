package com.sygic.travel.sdk.places.facade

import com.sygic.travel.sdk.common.LogicalOperator
import com.sygic.travel.sdk.common.api.formatApiRangeExpression
import com.sygic.travel.sdk.places.api.TripCategoryConverter
import com.sygic.travel.sdk.places.api.TripLevelConverter
import com.sygic.travel.sdk.places.model.Category
import com.sygic.travel.sdk.places.model.Level
import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.places.model.geo.LatLngBounds

/**
 * PlacesQuery contains values which define the places to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/1.0/#endpoint-get-places-list).
 */
@Suppress("MemberVisibilityCanBePrivate")
data class PlacesQuery(
	var query: String? = null,
	var bounds: LatLngBounds? = null,
	var categories: List<Category>? = null,
	var categoriesOperator: LogicalOperator = LogicalOperator.ALL,
	var categoriesNot: List<Category>? = null,
	var categoriesNotOperator: LogicalOperator = LogicalOperator.ALL,
	var tags: List<String>? = null,
	var tagsOperator: LogicalOperator = LogicalOperator.ALL,
	var tagsNot: List<String>? = null,
	var tagsNotOperator: LogicalOperator = LogicalOperator.ALL,
	var parentIds: List<String>? = null,
	var parentsOperator: LogicalOperator = LogicalOperator.ALL,
	var mapSpread: Int? = null,
	var mapTiles: List<String>? = null,
	var limit: Int? = null,
	var levels: List<Level>? = null,
	var hotelStarRatingMin: Int? = null,
	var hotelStarRatingMax: Int? = null,
	var customerRatingMin: Int? = null,
	var customerRatingMax: Int? = null,
	var preferredLocation: LatLng? = null
) {
	internal fun getLevelsApiQuery(): String? {
		return when (levels == null || levels!!.isEmpty()) {
			true -> null
			false -> levels!!.joinToString(LogicalOperator.ANY.apiExpression) { TripLevelConverter.toApiLevel(it) }
		}
	}

	internal fun getMapTilesApiQuery(): String? {
		return when (mapTiles == null || mapTiles!!.isEmpty()) {
			true -> null
			false -> mapTiles!!.joinToString(LogicalOperator.ANY.apiExpression)
		}
	}

	internal fun getCategoriesApiQuery(): String? {
		return when (categories == null || categories!!.isEmpty()) {
			true -> null
			false -> categories!!.joinToString(categoriesOperator.apiExpression) { TripCategoryConverter.toApiCategory(it) }
		}
	}

	internal fun getCategoriesNotApiQuery(): String? {
		return when (categoriesNot == null || categoriesNot!!.isEmpty()) {
			true -> null
			false -> categoriesNot!!.joinToString(categoriesNotOperator.apiExpression) { TripCategoryConverter.toApiCategory(it) }
		}
	}

	internal fun getTagsApiQuery(): String? {
		return when (tags == null || tags!!.isEmpty()) {
			true -> null
			false -> tags!!.joinToString(tagsOperator.apiExpression)
		}
	}

	internal fun getTagsNotApiQuery(): String? {
		return when (tagsNot == null || tagsNot!!.isEmpty()) {
			true -> null
			false -> tagsNot!!.joinToString(tagsNotOperator.apiExpression)
		}
	}

	internal fun getParentsApiQuery(): String? {
		return when (parentIds == null || parentIds!!.isEmpty()) {
			true -> null
			false -> parentIds!!.joinToString(parentsOperator.apiExpression)
		}
	}

	internal fun getHotelStarRatingApiQuery(): String? {
		return formatApiRangeExpression(hotelStarRatingMin, hotelStarRatingMax)
	}

	internal fun getCustomerRatingApiQuery(): String? {
		return formatApiRangeExpression(customerRatingMin, customerRatingMax)
	}
}
