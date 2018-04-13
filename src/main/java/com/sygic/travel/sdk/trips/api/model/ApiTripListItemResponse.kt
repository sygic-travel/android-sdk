package com.sygic.travel.sdk.trips.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiTripListItemResponse(
	val id: String,
	val owner_id: String,
	val name: String?,
	val version: Int,
	val url: String,
	val user_is_subscribed: Boolean,
	val updated_at: String,
	val is_deleted: Boolean,
	val privacy_level: String,
	val privileges: Privileges,
	val starts_on: String?,
	val ends_on: String?,
	val day_count: Int,
	val media: Media?
) {
	companion object {
		const val PRIVACY_PRIVATE = "private"
		const val PRIVACY_SHAREABLE = "shareable"
		const val PRIVACY_PUBLIC = "public"
	}

	@JsonSerializable
	class Privileges(
		val edit: Boolean,
		val manage: Boolean,
		val delete: Boolean
	)

	@JsonSerializable
	class Media(
		val square: MediaProperties,
		val landscape: MediaProperties,
		val portrait: MediaProperties,
		val video_preview: MediaProperties?
	) {
		@JsonSerializable
		class MediaProperties(
			val id: String,
			val url_template: String
		)
	}
}
