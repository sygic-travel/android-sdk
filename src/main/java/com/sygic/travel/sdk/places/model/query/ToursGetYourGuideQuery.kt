package com.sygic.travel.sdk.places.model.query

import com.sygic.travel.sdk.places.model.geo.Bounds
import java.util.Date

/**
 * ToursGetYourGuideQuery contains values which define the Get Your Guide tours to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/1.0/#section-tours).
 */
class ToursGetYourGuideQuery(
	val query: String?,
	val bounds: Bounds?,
	val parentPlaceId: String?,
	val tags: String?,
	val startDate: Date?,
	val endDate: Date?,
	val durationMin: Int?,
	val durationMax: Int?,
	val page: Int?,
	val count: Int?,
	val sortBy: SortBy?,
	val sortDirection: SortDirection?
) {
	internal fun getApiDurationQuery(): String? {
		return when {
			durationMin == null && durationMax == null -> null
			else -> (durationMin?.toString() ?: "") + ":" + (durationMax?.toString() ?: "")
		}
	}

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
