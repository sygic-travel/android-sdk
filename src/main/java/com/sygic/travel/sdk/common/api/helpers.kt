package com.sygic.travel.sdk.common.api

internal fun rangeFormatter(min: Int?, max: Int?): String? {
	return when {
		min != null && max != null -> "$min:$max"
		min != null -> "$min:"
		max != null -> ":$max"
		else -> null
	}
}
