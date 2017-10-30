package com.sygic.travel.sdk.common.database

import android.arch.persistence.room.TypeConverter

/**
 * Contains converter methods for working with database.
 */
internal class Converters {
	/**
	 * Converts a single String, separated by commas (','), to a list of Strings.
	 */
	@TypeConverter
	fun stringToList(value: String): List<String> {
		return value.split((",").toRegex(), 0)
	}

	/**
	 * Converts a list of String, a single String. Original string are separated by commas (',').
	 */
	@TypeConverter
	fun listToString(values: List<String>): String {
		return values.joinToString(",")
	}
}
