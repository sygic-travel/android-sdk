package com.sygic.travel.sdk.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

internal object DateTimeHelper {
	private val timezone = TimeZone.getTimeZone("UTC")
	private val outDateTimeFormatTz = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
	private val outDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
	private val outDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
	private val parseDateTimeFormatTz = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
	private val parseDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
	private val parseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

	init {
		outDateTimeFormatTz.timeZone = timezone
		outDateTimeFormat.timeZone = timezone
		outDateFormat.timeZone = timezone
		parseDateTimeFormatTz.timeZone = timezone
		parseDateTimeFormat.timeZone = timezone
		parseDateFormat.timeZone = timezone
	}

	fun timestampToDatetime(timestamp: Date?): String? {
		return when (timestamp) {
			null -> null
			else -> outDateTimeFormatTz.format(timestamp)
		}
	}

	fun timestampToDatetimeLocal(timestamp: Date?): String? {
		return when (timestamp) {
			null -> null
			else -> outDateTimeFormat.format(timestamp)
		}
	}

	fun timestampToDate(timestamp: Date?): String? {
		return when (timestamp) {
			null -> null
			else -> outDateFormat.format(timestamp)
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
				parseDateTimeFormatTz.parse(string)
			}
		}
	}

	fun datetimeLocalToTimestamp(datetime: String?): Date? {
		return when (datetime) {
			null -> null
			else -> parseDateTimeFormat.parse(datetime)
		}
	}

	fun dateToTimestamp(date: String?): Date? {
		return when (date) {
			null -> null
			else -> parseDateFormat.parse(date)
		}
	}

	fun now(): Date {
		return Date()
	}
}
