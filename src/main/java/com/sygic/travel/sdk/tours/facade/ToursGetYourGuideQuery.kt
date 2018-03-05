package com.sygic.travel.sdk.tours.facade

import com.sygic.travel.sdk.common.api.rangeFormatter
import com.sygic.travel.sdk.places.model.geo.Bounds
import java.util.Date

/**
 * ToursGetYourGuideQuery contains values which define the Get Your Guide tours to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/1.0/#section-tours).
 */
class ToursGetYourGuideQuery {
	var query: String? = null
	var bounds: Bounds? = null
	var parentPlaceId: String? = null
	var tags: String? = null
	var startDate: Date? = null
	var endDate: Date? = null
	var durationMin: Int? = null
	var durationMax: Int? = null
	var page: Int? = null
	var count: Int? = null
	var sortBy: SortBy? = null
	var sortDirection: SortDirection? = null

	internal fun getApiDurationQuery(): String? {
		return rangeFormatter(durationMin, durationMax)
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
