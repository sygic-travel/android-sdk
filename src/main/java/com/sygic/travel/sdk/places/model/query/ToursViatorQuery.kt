package com.sygic.travel.sdk.places.model.query

/**
 * ToursViatorQuery contains values which define the Viator tours to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/1.0/#section-tours).
 */
class ToursViatorQuery(
	val parentPlaceId: String,
	val page: Int?,
	val sortBy: SortBy?,
	val sortDirection: SortDirection?
) {

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
