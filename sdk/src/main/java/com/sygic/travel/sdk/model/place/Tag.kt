package com.sygic.travel.sdk.model.place

class Tag {
	var key: String? = null
	var name: String? = null

	override fun equals(other: Any?): Boolean {
		if (other != null && other is Tag) {
			return key == other.key
		} else {
			return false
		}
	}

	override fun hashCode(): Int {
		return key!!.hashCode()
	}

}
