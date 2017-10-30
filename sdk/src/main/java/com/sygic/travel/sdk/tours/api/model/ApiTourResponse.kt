package com.sygic.travel.sdk.tours.api.model

import com.sygic.travel.sdk.tours.model.Tour

/**
 * ApiResponse that contains a list of Tour classes.
 */
class ApiTourResponse(
	private val tours: List<ApiTourItemResponse>
) {
	fun getTours(): List<Tour> {
		return tours.map { it.fromApi() }
	}
}
