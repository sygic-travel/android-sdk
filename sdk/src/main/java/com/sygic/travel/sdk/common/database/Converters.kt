package com.sygic.travel.sdk.common.database

import android.arch.persistence.room.TypeConverter
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.directions.model.DirectionAvoid
import com.sygic.travel.sdk.directions.model.DirectionMode

/**
 * Contains converter methods for working with database.
 */
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
	fun privacyLevelToString(level: TripInfo.PrivacyLevel?): String? {
		return when (level) {
			null -> null
			else -> level.name
		}
	}

	@TypeConverter
	fun stringToPrivacyLevel(level: String?): TripInfo.PrivacyLevel? {
		return when (level) {
			null -> null
			else -> TripInfo.PrivacyLevel.valueOf(level)
		}
	}

	@TypeConverter
	fun modeToString(mode: DirectionMode?): String? {
		return when (mode) {
			null -> null
			else -> mode.name
		}
	}

	@TypeConverter
	fun stringToMode(mode: String?): DirectionMode? {
		return when (mode) {
			null -> null
			else -> DirectionMode.valueOf(mode)
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
}
