package com.sygic.travel.sdk.model.query

/**
 * ToursQuery contains values which define the tours to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/0.2/#section-tours).
 */
class ToursQuery(
        val destinationId: String,
        val page: Int?,
        val sortBy: SortBy,
        val sortDirection: SortDirection
) {

    enum class SortBy constructor(sortBy: String) {
        PRICE("price"),
        RATING("rating"),
        TOP_SELLERS("top_sellers");

        var string: String
            internal set

        init {
            this.string = sortBy
        }
    }

    enum class SortDirection constructor(sortDirection: String) {
        ASC("asc"),
        DESC("desc");

        var string: String
            internal set

        init {
            this.string = sortDirection
        }
    }
}