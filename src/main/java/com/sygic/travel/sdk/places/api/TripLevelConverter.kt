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
			LEVEL_COUNTY -> Level.COUNTY
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

	fun toApiLevel(level: Level): String {
		return when (level) {
			Level.CONTINENT -> LEVEL_CONTINENT
			Level.COUNTRY -> LEVEL_COUNTRY
			Level.STATE -> LEVEL_STATE
			Level.REGION -> LEVEL_REGION
			Level.COUNTY -> LEVEL_COUNTY
			Level.CITY -> LEVEL_CITY
			Level.TOWN -> LEVEL_TOWN
			Level.VILLAGE -> LEVEL_VILLAGE
			Level.SETTLEMENT -> LEVEL_SETTLEMENT
			Level.LOCALITY -> LEVEL_LOCALITY
			Level.NEIGHBOURHOOD -> LEVEL_NEIGHBOURHOOD
			Level.ARCHIPELAGO -> LEVEL_ARCHIPELAGO
			Level.ISLAND -> LEVEL_ISLAND
			Level.POI -> LEVEL_POI
		}
	}
}
