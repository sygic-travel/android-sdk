package com.sygic.travel.sdk.trips.api.model

open class ApiTripListItemResponse(
	val id: String,
	val owner_id: String,
	val name: String?,
	val version: Int,
	val url: String,
	val updated_at: String,
	val is_deleted: Boolean,
	val privacy_level: String,
	val privileges: Privileges,
	val starts_on: String?,
	val ends_on: String?,
	val days_count: Int,
	val media: Media?
) {
	companion object {
		const val PRIVACY_PRIVATE = "private"
		const val PRIVACY_SHAREABLE = "shareable"
		const val PRIVACY_PUBLIC = "public"
	}

	class Privileges(
		val edit: Boolean,
		val manage: Boolean,
		val delete: Boolean
	)

	class Media(
		val square: MediaProperties,
		val landscape: MediaProperties,
		val portrait: MediaProperties,
		val video_preview: MediaProperties?
	) {
		class MediaProperties(
			val id: String,
			val url_template: String
		)
	}
}
