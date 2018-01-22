package com.sygic.travel.sdk.trips.api.model

internal class ApiTripItemResponse(
	id: String,
	owner_id: String,
	name: String?,
	version: Int,
	url: String,
	updated_at: String,
	is_deleted: Boolean,
	privacy_level: String,
	privileges: Privileges,
	starts_on: String?,
	ends_on: String?,
	days_count: Int,
	media: Media?,
	val days: List<Day>,
	val destinations: List<String>
) : ApiTripListItemResponse(
	id,
	owner_id,
	name,
	version,
	url,
	updated_at,
	is_deleted,
	privacy_level,
	privileges,
	starts_on,
	ends_on,
	days_count,
	media
) {
	class Day(
		val itinerary: List<DayItem>,
		val note: String?
	) {
		class DayItem(
			val place_id: String,
			val start_time: Int?,
			val duration: Int?,
			val note: String?,
			val transport_from_previous: Transport?
		) {
			class Transport(
				val mode: String,
				val avoid: List<String>,
				val type: String,
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
					const val MODE_TRAIN = "train"
					const val AVOID_FERRIES = "ferries"
					const val AVOID_HIGHWAYS = "highways"
					const val AVOID_TOLLS = "tolls"
					const val AVOID_UNPAVED = "unpaved"
				}

				class Waypoint(
					val placeId: String?,
					val location: Location
				) {
					class Location(
						val lat: Float,
						val lng: Float
					)
				}
			}
		}
	}
}
