package com.sygic.travel.sdk.places.facade

import com.sygic.travel.sdk.places.model.geo.Bounds

/**
 * PlacesQuery contains values which define the places to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/1.0/#endpoint-get-places-list).
 */
class PlacesQuery {
	var query: String? = null
	var bounds: Bounds? = null
	var categories: List<String>? = null
	var categoriesOperator = LogicOperator.OR
	var tags: List<String>? = null
	var tagsOperator = LogicOperator.OR
	var parents: List<String>? = null
	var parentsOperator = LogicOperator.OR
	var mapSpread: Int? = null
	var mapTiles: List<String>? = null
	var limit: Int? = null
	var levels: List<String>? = null

	internal fun getLevelsApiQuery(): String? {
		return when (levels == null || levels!!.isEmpty()) {
			true -> null
			false -> levels!!.joinToString(LogicOperator.OR.apiOperator)
		}
	}

	internal fun getMapTilesApiQuery(): String? {
		return when (mapTiles == null || mapTiles!!.isEmpty()) {
			true -> null
			false -> mapTiles!!.joinToString(LogicOperator.OR.apiOperator)
		}
	}

	internal fun getCategoriesApiQuery(): String? {
		return when (categories == null || categories!!.isEmpty()) {
			true -> null
			false -> categories!!.joinToString(categoriesOperator.apiOperator)
		}
	}

	internal fun getTagsApiQuery(): String? {
		return when (tags == null || tags!!.isEmpty()) {
			true -> null
			false -> tags!!.joinToString(tagsOperator.apiOperator)
		}
	}

	internal fun getParentsApiQuery(): String? {
		return when (parents == null || parents!!.isEmpty()) {
			true -> null
			false -> parents!!.joinToString(parentsOperator.apiOperator)
		}
	}

	enum class LogicOperator constructor(internal val apiOperator: String) {
		AND(","),
		OR("%7C");
	}
}
