package com.sygic.travel.sdk.tours.api.model

import com.sygic.travel.sdk.tours.model.Tour
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiTourResponse(
	val tours: List<ApiTourItemResponse>
) {
	fun fromApi(): List<Tour> {
		return tours.map { it.fromApi() }
	}
}
