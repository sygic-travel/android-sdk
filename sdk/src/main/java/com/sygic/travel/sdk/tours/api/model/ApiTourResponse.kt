package com.sygic.travel.sdk.tours.api.model

import com.sygic.travel.sdk.tours.model.Tour

internal class ApiTourResponse(
	private val tours: List<ApiTourItemResponse>
) {
	fun getTours(): List<Tour> {
		return tours.map { it.fromApi() }
	}
}
