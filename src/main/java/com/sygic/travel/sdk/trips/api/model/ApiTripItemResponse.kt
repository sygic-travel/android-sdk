package com.sygic.travel.sdk.trips.api.model

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
internal class ApiTripItemResponse(
	val id: String,
	val owner_id: String,
	val name: String?,
	val version: Int,
	val url: String,
	val updated_at: String,
	val is_deleted: Boolean,
	val privacy_level: String,
	val privileges: ApiTripListItemResponse.Privileges,
	val starts_on: String?,
	val ends_on: String?,
	val day_count: Int,
	val media: ApiTripListItemResponse.Media?,
	val days: List<Day>,
	val destinations: List<String>
) {
	@JsonSerializable
	class Day(
		val itinerary: List<DayItem>,
		val note: String?
	) {
		@JsonSerializable
		class DayItem(
			val place_id: String,
			val start_time: Int?,
			val duration: Int?,
			val note: String?,
			val transport_from_previous: Transport?
		) {
			@JsonSerializable
			class Transport(
				val mode: String,
				val avoid: List<String>,
				val start_time: Int?,
				val duration: Int?,
				val note: String?,
				val waypoints: List<Waypoint>
			) {
				companion object {
					const val MODE_BIKE = "bike"
					const val MODE_BOAT = "boat"
					const val MODE_BUS = "bus"
					const val MODE_CAR = "car"
					const val MODE_PEDESTRIAN = "pedestrian"
					const val MODE_PLANE = "plane"
					const val MODE_PUBLIC_TRANSIT = "public_transit"
					const val MODE_TRAIN = "train"
					const val AVOID_FERRIES = "ferries"
					const val AVOID_HIGHWAYS = "highways"
					const val AVOID_TOLLS = "tolls"
					const val AVOID_UNPAVED = "unpaved"
				}

				@JsonSerializable
				class Waypoint(
					val placeId: String?,
					val location: Location
				) {
					@JsonSerializable
					class Location(
						val lat: Float,
						val lng: Float
					)
				}
			}
		}
	}
}
