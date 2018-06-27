package com.sygic.travel.sdk.tours.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.tours.model.Tour

@JsonClass(generateAdapter = true)
internal class ApiTourResponse(
	val tours: List<ApiTourItemResponse>
) {
	fun fromApi(): List<Tour> {
		return tours.map { it.fromApi() }
	}
}
