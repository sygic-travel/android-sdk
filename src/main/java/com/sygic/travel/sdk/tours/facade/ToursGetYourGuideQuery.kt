package com.sygic.travel.sdk.tours.facade

import com.sygic.travel.sdk.common.api.formatApiRangeExpression
import com.sygic.travel.sdk.places.model.geo.LatLngBounds
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

/**
 * ToursGetYourGuideQuery contains values which define the Get Your Guide tours to be fetched.
 * To see what the parameters mean check the
 * [API Documentation](http://docs.sygictravelapi.com/1.0/#section-tours).
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
data class ToursGetYourGuideQuery constructor(
	var query: String? = null,
	var bounds: LatLngBounds? = null,
	var parentPlaceId: String? = null,
	var tags: String? = null,
	var startDate: LocalDateTime? = null,
	var endDate: LocalDateTime? = null,
	var durationMin: Duration? = null,
	var durationMax: Duration? = null,
	var page: Int? = null,
	var count: Int? = null,
	var sortBy: SortBy? = null,
	var sortDirection: SortDirection? = null
) {
	internal fun getApiDurationQuery(): String? {
		return formatApiRangeExpression(durationMin?.seconds?.toInt(), durationMax?.seconds?.toInt())
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
