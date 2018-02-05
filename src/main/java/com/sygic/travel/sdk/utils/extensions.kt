package com.sygic.travel.sdk.utils

import java.util.Date

fun Long?.asDate(): Date? {
	return when (this) {
		null -> null
		else -> Date(this * 1000)
	}
}

val Date?.timeSeconds: Long?
	get() {
		return when (this) {
			null -> null
			else -> this.time / 1000
		}
	}
