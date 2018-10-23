package com.sygic.travel.sdk.trips.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class ApiTripItemResponse(
	val id: String,
	val owner_id: String,
	val name: String?,
	val version: Int,
	val url: String,
	val user_is_subscribed: Boolean,
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
	@JsonClass(generateAdapter = true)
	internal class Day(
		val itinerary: List<DayItem>,
		val note: String?
	) {
		@JsonClass(generateAdapter = true)
		internal class DayItem(
			val place_id: String,
			val start_time: Int?,
			val duration: Int?,
			val note: String?,
			val transport_from_previous: Transport?
		) {
			@JsonClass(generateAdapter = true)
			internal class Transport(
				val mode: String,
				val avoid: List<String>,
				val start_time: Int?,
				val duration: Int?,
				val note: String?,
				val route_id: String?,
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

				@JsonClass(generateAdapter = true)
				internal class Waypoint(
					val place_id: String?,
					val location: Location
				) {
					@JsonClass(generateAdapter = true)
					internal class Location(
						val lat: Double,
						val lng: Double
					)
				}
			}
		}
	}
}
