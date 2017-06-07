package com.sygic.travel.sdk.model.query

import android.text.TextUtils

import com.sygic.travel.sdk.model.geo.Bounds

/**
 *
 * Query contains values which define the places to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/0.2/#endpoint-get-places-list).
 */
class Query {
    var query: String? = null
    var bounds: Bounds? = null
    var categories: List<String>? = null
    var categoriesOperator = Operator.OR
    var tags: List<String>? = null
    var tagsOperator = Operator.OR
    var parents: List<String>? = null
    var parentsOperator = Operator.OR
    var mapSpread: Int? = null
    var mapTiles: List<String>? = null
    var limit: Int? = null
    var levels: List<String>? = null

    /**
     * Enumaration of possible logical operators.
     */
    enum class Operator private constructor(operator: String) {
        AND(","),
        OR("%7C");

        var operator: String
            internal set

        init {
            this.operator = operator
        }
    }

    constructor() {}

    /**
     *
     * Query contains values which define the places to be fetched.
     * To see what the parameters mean check the
     * [API Documentation](http://docs.sygictravelapi.com/0.2/#endpoint-get-places-list).
     */
    constructor(
            query: String,
            bounds: Bounds,
            categories: List<String>,
            tags: List<String>,
            parents: List<String>,
            mapSpread: Int?,
            mapTiles: List<String>,
            limit: Int?,
            levels: List<String>
    ) {
        this.query = query
        this.bounds = bounds
        this.categories = categories
        this.tags = tags
        this.parents = parents
        this.mapSpread = mapSpread
        this.mapTiles = mapTiles
        this.limit = limit
        this.levels = levels
    }

    val levelsQueryString: String?
        get() {
            if (levels == null || levels!!.isEmpty()) {
                return null
            } else {
                return TextUtils.join(Operator.OR.operator, levels!!.toTypedArray())
            }
        }

    val mapTilesQueryString: String?
        get() {
            if (mapTiles == null || mapTiles!!.isEmpty()) {
                return null
            } else {
                return TextUtils.join(Operator.OR.operator, mapTiles!!.toTypedArray())
            }
        }

    val categoriesQueryString: String?
        get() {
            if (categories == null || categories!!.isEmpty()) {
                return null
            } else {
                return TextUtils.join(categoriesOperator.operator, categories!!.toTypedArray())
            }
        }

    val tagsQueryString: String?
        get() {
            if (tags == null || tags!!.isEmpty()) {
                return null
            } else {
                return TextUtils.join(tagsOperator.operator, tags!!.toTypedArray())
            }
        }

    val parentsQueryString: String?
        get() {
            if (parents == null || parents!!.isEmpty()) {
                return null
            } else {
                return TextUtils.join(parentsOperator.operator, parents!!.toTypedArray())
            }
        }

    val boundsQueryString: String?
        get() = bounds?.toQueryString()
}
