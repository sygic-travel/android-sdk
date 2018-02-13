package com.sygic.travel.sdk.tours.facade

/**
 * ToursViatorQuery contains values which define the Viator tours to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/1.0/#section-tours).
 */
class ToursViatorQuery(
	var parentPlaceId: String
) {
	var page: Int? = null
	var sortBy: SortBy? = null
	var sortDirection: SortDirection? = null

	enum class SortBy constructor(internal val apiSortBy: String) {
		PRICE("price"),
		RATING("rating"),
		TOP_SELLERS("top_sellers");
	}

	enum class SortDirection constructor(internal val apiSortDirection: String) {
		ASC("asc"),
		DESC("desc");
	}
}
