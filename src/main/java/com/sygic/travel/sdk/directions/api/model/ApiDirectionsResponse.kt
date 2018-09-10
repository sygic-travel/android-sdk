package com.sygic.travel.sdk.directions.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class ApiDirectionsResponse(
	val path: List<Directions>
) {
	@JsonClass(generateAdapter = true)
	internal class Directions(
		val directions: List<Direction>
	) {
		@JsonClass(generateAdapter = true)
		internal class Direction(
			val route_id: String?,
			val distance: Int,
			val duration: Int,
			val mode: String,
			val source: String,
			val transfer_count: Int,
			val legs: List<Legs>,
			val attributions: List<Attribution>
		) {
			@JsonClass(generateAdapter = true)
			internal class Legs(
				val start_time: DirectionTime,
				val end_time: DirectionTime,
				val duration: Int,
				val distance: Int,
				val mode: String,
				val polyline: String,
				val origin: LegStop,
				val destination: LegStop,
				val intermediate_stops: List<LegStop>,
				val display_info: DisplayInfo,
				val attribution: Attribution?
			) {
				companion object {
					const val MODE_BIKE = "bike"
					const val MODE_BOAT = "boat"
					const val MODE_BUS = "bus"
					const val MODE_CAR = "car"
					const val MODE_FUNICULAR = "funicular"
					const val MODE_PEDESTRIAN = "pedestrian"
					const val MODE_PLANE = "plane"
					const val MODE_SUBWAY = "subway"
					const val MODE_TAXI = "taxi"
					const val MODE_TRAIN = "train"
					const val MODE_TRAM = "tram"
				}
				@JsonClass(generateAdapter = true)
				internal class LegStop(
					val name: String?,
					val location: Location,
					val arrival_at: DirectionTime,
					val departure_at: DirectionTime,
					val planned_arrival_at: DirectionTime,
					val planned_departure_at: DirectionTime
				)

				@JsonClass(generateAdapter = true)
				internal class DirectionTime(
					val datetime_local: String?,
					val datetime: String?
				)

				@JsonClass(generateAdapter = true)
				internal class DisplayInfo(
					val headsign: String?,
					val name_short: String?,
					val name_long: String?,
					val line_color: String?,
					val display_mode: String?
				)
			}

			companion object {
				const val MODE_CAR = "car"
				const val MODE_PEDESTRIAN = "pedestrian"
				const val MODE_PUBLIC_TRANSIT = "public_transit"
			}

			@JsonClass(generateAdapter = true)
			internal class Location(
				val lat: Double,
				val lng: Double
			)

			@JsonClass(generateAdapter = true)
			internal class Attribution(
				val name: String,
				val url: String?,
				val logo_url: String?,
				val license: String?
			)
		}
	}
}
