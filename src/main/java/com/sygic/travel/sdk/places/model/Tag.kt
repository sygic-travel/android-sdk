package com.sygic.travel.sdk.places.model

data class Tag(
	val key: String,
	val name: String
) {
	override fun equals(other: Any?): Boolean {
		return if (other != null && other is Tag) {
			key == other.key
		} else {
			false
		}
	}

	override fun hashCode(): Int {
		return key.hashCode()
	}
}
