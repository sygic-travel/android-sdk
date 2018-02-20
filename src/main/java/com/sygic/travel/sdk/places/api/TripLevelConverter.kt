package com.sygic.travel.sdk.places.api

import com.sygic.travel.sdk.places.model.Level

internal object TripLevelConverter {
	private const val LEVEL_CONTINENT = "continent"
	private const val LEVEL_COUNTRY = "country"
	private const val LEVEL_STATE = "state"
	private const val LEVEL_REGION = "region"
	private const val LEVEL_COUNTY = "county"
	private const val LEVEL_CITY = "city"
	private const val LEVEL_TOWN = "town"
	private const val LEVEL_VILLAGE = "village"
	private const val LEVEL_SETTLEMENT = "settlement"
	private const val LEVEL_LOCALITY = "locality"
	private const val LEVEL_NEIGHBOURHOOD = "neighbourhood"
	private const val LEVEL_ARCHIPELAGO = "archipelago"
	private const val LEVEL_ISLAND = "island"
	private const val LEVEL_POI = "poi"

	fun fromApiLevel(level: String): Level {
		return when (level) {
			LEVEL_CONTINENT -> Level.CONTINENT
			LEVEL_COUNTRY -> Level.COUNTRY
			LEVEL_STATE -> Level.STATE
			LEVEL_REGION -> Level.REGION
			LEVEL_COUNTY -> Level.COUNTRY
			LEVEL_CITY -> Level.CITY
			LEVEL_TOWN -> Level.TOWN
			LEVEL_VILLAGE -> Level.VILLAGE
			LEVEL_SETTLEMENT -> Level.SETTLEMENT
			LEVEL_LOCALITY -> Level.LOCALITY
			LEVEL_NEIGHBOURHOOD -> Level.NEIGHBOURHOOD
			LEVEL_ARCHIPELAGO -> Level.ARCHIPELAGO
			LEVEL_ISLAND -> Level.ISLAND
			LEVEL_POI -> Level.POI
			else -> Level.POI
		}
	}
}
