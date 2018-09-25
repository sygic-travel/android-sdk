package com.sygic.travel.sdk.common.database

import android.arch.persistence.room.TypeConverter
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.places.model.geo.LatLng
import com.sygic.travel.sdk.trips.model.TripItemTransportMode
import com.sygic.travel.sdk.trips.model.TripItemTransportWaypoint
import com.sygic.travel.sdk.trips.model.TripPrivacyLevel

internal class Converters {
	@TypeConverter
	fun stringListToString(list: ArrayList<String>?): String? {
		return when (list) {
			null -> null
			else -> list.joinToString(",")
		}
	}

	@TypeConverter
	fun stringToStringList(string: String?): ArrayList<String>? {
		return when (string) {
			null -> null
			else -> ArrayList(string.split(","))
		}
	}

	@TypeConverter
	fun privacyLevelToString(level: TripPrivacyLevel?): String? {
		return when (level) {
			null -> null
			else -> level.name
		}
	}

	@TypeConverter
	fun stringToPrivacyLevel(level: String?): TripPrivacyLevel? {
		return when (level) {
			null -> null
			else -> TripPrivacyLevel.valueOf(level)
		}
	}

	@TypeConverter
	fun tripModeToString(mode: TripItemTransportMode?): String? {
		return when (mode) {
			null -> null
			else -> mode.name
		}
	}

	@TypeConverter
	fun stringToTripMode(mode: String?): TripItemTransportMode? {
		return when (mode) {
			null -> null
			else -> TripItemTransportMode.valueOf(mode)
		}
	}

	@TypeConverter
	fun avoidListToString(avoidList: ArrayList<DirectionAvoid>?): String? {
		return when (avoidList) {
			null -> ""
			else -> avoidList.joinToString(",") { it.name }
		}
	}

	@TypeConverter
	fun stringToAvoidList(avoidString: String?): ArrayList<DirectionAvoid>? {
		return when (avoidString) {
			null, "" -> arrayListOf()
			else -> ArrayList(avoidString.split(",").map { DirectionAvoid.valueOf(it) })
		}
	}

	@TypeConverter
	fun tripItemTransportWaypointToString(avoidList: ArrayList<TripItemTransportWaypoint>?): String? {
		return when (avoidList) {
			null -> ""
			else -> avoidList.joinToString(",") { it.placeId + ';' + it.location.lat + ';' + it.location.lng }
		}
	}

	@TypeConverter
	fun stringToTripItemTransportWaypoint(avoidString: String?): ArrayList<TripItemTransportWaypoint>? {
		return when (avoidString) {
			null, "" -> arrayListOf()
			else -> ArrayList(avoidString.split(",").map {
				val parts = it.split(";")
				TripItemTransportWaypoint(
					placeId = when (parts[0].isEmpty()) {
						true -> null
						false -> parts[0]
					},
					location = LatLng(
						lat = parts[1].toDouble(),
						lng = parts[2].toDouble()
					)
				)
			})
		}
	}
}
