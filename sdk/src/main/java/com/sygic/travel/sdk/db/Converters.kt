package com.sygic.travel.sdk.db

import android.arch.persistence.room.TypeConverter

class Converters {
	@TypeConverter
	fun stringToList(value: String): List<String> {
		return value.split((",").toRegex(), 0)
	}

	@TypeConverter
	fun listToString(values: List<String>): String {
		return values.joinToString(",")
	}
}
