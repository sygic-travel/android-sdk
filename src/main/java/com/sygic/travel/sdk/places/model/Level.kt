package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.api.TripLevelConverter

@Suppress("unused")
enum class Level {
	CONTINENT,
	COUNTRY,
	STATE,
	REGION,
	COUNTY,
	CITY,
	TOWN,
	VILLAGE,
	SETTLEMENT,
	LOCALITY,
	NEIGHBOURHOOD,
	ARCHIPELAGO,
	ISLAND,
	POI;

	fun toApiExpression(): String {
		return TripLevelConverter.toApiLevel(this)
	}
}
