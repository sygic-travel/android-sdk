package com.sygic.travel.sdk.places.model.query

/**
 * ToursGetYourGuideQuery contains values which define the Get Your Guide tours to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/1.0/#section-tours).
 */
class ToursGetYourGuideQuery(
	val query: String?,
	val bounds: String?,
	val parentPlaceId: String?,
	val tags: String?,
	val from: String?,
	val to: String?,
	val duration: String?,
	val page: Int?,
	val count: Int?,
	val sortBy: SortBy?,
	val sortDirection: SortDirection?
) {

	enum class SortBy constructor(internal val apiSortBy: String) {
		PRICE("price"),
		RATING("rating"),
		DURATION("duration"),
		POPULARITY("popularity");
	}

	enum class SortDirection constructor(internal val apiSortDirection: String) {
		ASC("asc"),
		DESC("desc");
	}
}
