package com.sygic.travel.sdk.synchronization.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class ApiChangesResponse(
	val changes: List<ChangeEntry>
) {
	@JsonClass(generateAdapter = true)
	internal class ChangeEntry(
		val type: String,
		val id: String?,
		val change: String,
		val version: Int?
	) {
		companion object {
			const val TYPE_TRIP = "trip"
			const val TYPE_FAVORITE = "favorite"
			const val TYPE_SETTINGS = "settings"
			const val TYPE_CUSTOM_PLACE = "custom_place"
			const val CHANGE_UPDATED = "updated"
			const val CHANGE_DELETED = "deleted"
		}
	}
}
