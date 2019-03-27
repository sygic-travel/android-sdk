package com.sygic.travel.sdk.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

internal object DateTimeHelper {
	private val timezone = TimeZone.getTimeZone("UTC")

	fun timestampToDatetime(timestamp: Date?): String? {
		return when (timestamp) {
			null -> null
			else -> {
				val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
				format.timeZone = timezone
				format.format(timestamp)
			}
		}
	}

	fun timestampToDatetimeLocal(timestamp: Date?): String? {
		return when (timestamp) {
			null -> null
			else -> {
				val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
				format.timeZone = timezone
				format.format(timestamp)
			}
		}
	}

	fun timestampToDate(timestamp: Date?): String? {
		return when (timestamp) {
			null -> null
			else -> {
				val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
				format.timeZone = timezone
				format.format(timestamp)
			}
		}
	}

	fun datetimeToTimestamp(datetime: String?): Date? {
		return when (datetime) {
			null -> null
			else -> {
				var string = datetime
				string = string.replace("Z", "+00:00")
				if (string.getOrNull(22) == ':') {
					// strip : in timezone part
					string = string.substring(0, 22) + string.substring(23)
				}
				val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
				format.timeZone = timezone
				format.parse(string)
			}
		}
	}

	fun datetimeLocalToTimestamp(datetime: String?): Date? {
		return when (datetime) {
			null -> null
			else -> {
				val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
				format.timeZone = timezone
				format.parse(datetime)
			}
		}
	}

	fun dateToTimestamp(date: String?): Date? {
		return when (date) {
			null -> null
			else -> {
				val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
				format.timeZone = timezone
				format.parse(date)
			}
		}
	}

	fun now(): Date {
		return Date()
	}
}
